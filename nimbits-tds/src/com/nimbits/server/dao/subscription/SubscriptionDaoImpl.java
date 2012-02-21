/*
 * Copyright (c) 2010 Tonic Solutions LLC.
 *
 * http://www.nimbits.com
 *
 *
 * Licensed under the GNU GENERAL PUBLIC LICENSE, Version 3.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.gnu.org/licenses/gpl.html
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the license is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, eitherexpress or implied. See the License for the specific language governing permissions and limitations under the License.
 */

package com.nimbits.server.dao.subscription;

import com.nimbits.*;
import com.nimbits.client.enums.*;
import com.nimbits.client.model.entity.*;
import com.nimbits.client.model.point.*;
import com.nimbits.client.model.subscription.*;
import com.nimbits.client.model.user.*;
import com.nimbits.server.orm.*;
import com.nimbits.server.subscription.*;

import javax.jdo.*;
import java.util.*;

/**
 * Created by Benjamin Sautner
 * User: BSautner
 * Date: 1/17/12
 * Time: 4:18 PM
 */
public class SubscriptionDaoImpl implements SubscriptionTransactions {
    final private User user;

    public SubscriptionDaoImpl(User u) {
        this.user = u;
    }


    @Override
    public void subscribe(Entity entity, Subscription subscription) {
         addOrUpdateSubscription(entity, subscription);
    }

    private void addOrUpdateSubscription(Entity entity, Subscription subscription)  {

        final PersistenceManager pm = PMF.get().getPersistenceManager();
        List<SubscriptionEntity> results;


        try {

            Query q = pm.newQuery(SubscriptionEntity.class, "uuid==u");
            q.declareParameters("String u");
            q.setRange(0, 1);
            results = (List<SubscriptionEntity>) q.execute(entity.getEntity());
            if (results.size() > 0) {
                SubscriptionEntity result = results.get(0);
                Transaction tx = pm.currentTransaction();
                tx.begin();
                result.setNotifyMethod(subscription.getNotifyMethod());
                result.setSubscriptionType(subscription.getSubscriptionType());
                result.setLastSent(subscription.getLastSent());
                result.setMaxRepeat(subscription.getMaxRepeat());
                result.setEnabled(subscription.getEnabled());
                result.setNotifyFormatJson(subscription.getNotifyFormatJson());
                tx.commit();
                //retObj = EntityTransactionFactory.getInstance(user).getEntityByUUID(result.getUuid());
                pm.flush();


            }
            else {
                SubscriptionEntity s = new SubscriptionEntity(subscription);
                pm.makePersistent(s);

            }


        }
        finally {
            pm.close();
        }

    }

    public Subscription readSubscription(final Entity entity)  {

        final PersistenceManager pm = PMF.get().getPersistenceManager();
        List<SubscriptionEntity> results;
        Subscription retObj = null;
        try {
            Query q = pm.newQuery(SubscriptionEntity.class, "uuid==u");
            q.declareParameters("String u");
            q.setRange(0, 1);
            results = (List<SubscriptionEntity>) q.execute(entity.getEntity());
            if (results.size() > 0) {
                SubscriptionEntity result = results.get(0);
                retObj = SubscriptionFactory.createSubscription(result);
            }
            return retObj;
        }
        finally {
            pm.close();
        }

    }



    @Override
    public List<Subscription> getSubscriptionsToPoint(final Point point) {
        final PersistenceManager pm = PMF.get().getPersistenceManager();
        List<Subscription> results;
        List<Subscription> retObj;
        try {
            Query q = pm.newQuery(SubscriptionEntity.class, "subscribedEntity==p && enabled==e");
            q.declareParameters("String p, Boolean e");
            results = (List<Subscription>) q.execute(point.getUUID(), true);
            retObj = SubscriptionFactory.createSubscriptions(results);
            return retObj;
        }
        finally {
            pm.close();
        }
    }

    @Override
    public List<Subscription> getSubscriptionsToPointByType(final Point point, final SubscriptionType type) {
        final PersistenceManager pm = PMF.get().getPersistenceManager();
        List<Subscription> results;
        List<Subscription> retObj;
        try {
            Query q = pm.newQuery(SubscriptionEntity.class, "subscribedEntity==p && subscriptionType==t && enabled==e" );
            q.declareParameters("String p, Integer t, Boolean e");
            results = (List<Subscription>) q.execute(point.getUUID(), type.getCode(), true);
            retObj = SubscriptionFactory.createSubscriptions(results);
            return retObj;
        }
        finally {
            pm.close();
        }
    }
    @Override
    public void updateSubscriptionLastSent(Subscription subscription) {
        final PersistenceManager pm = PMF.get().getPersistenceManager();

            try {
                List<SubscriptionEntity> results;
                Query q = pm.newQuery(SubscriptionEntity.class, "uuid==u");
                q.declareParameters("String u");
                q.setRange(0, 1);
                results = (List<SubscriptionEntity>) q.execute(subscription.getUuid());
                if (results.size() > 0) {
                    Transaction tx = pm.currentTransaction();
                    tx.begin();
                    SubscriptionEntity result = results.get(0);
                    result.setLastSent(new Date());
                    tx.commit();
                }
            } finally {
                pm.close();
            }


    }

}
