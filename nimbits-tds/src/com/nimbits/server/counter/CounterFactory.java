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

package com.nimbits.server.counter;

/**
 * Created by Benjamin Sautner
 * User: BSautner
 * Date: 12/19/11
 * Time: 5:00 PM
 */

import com.nimbits.*;
import com.nimbits.server.dao.counter.*;
import com.nimbits.server.orm.*;

import javax.jdo.*;

/**
 * Finds or creates a sharded counter with the desired name.
 *
 */
public class CounterFactory {

    public ShardedCounter getCounter(final String name) {
        ShardedCounter counter = new ShardedCounter(name);
        if (counter.isInDatastore()) {
            return counter;
        } else {
            return null;
        }
    }

    public ShardedCounter createCounter(final String name) {
        ShardedCounter counter = new ShardedCounter(name);

        DatastoreCounter counterEntity = new DatastoreCounter(name, 0);
        PersistenceManager pm = PMF.get().getPersistenceManager();
        try {
            pm.makePersistent(counterEntity);
        } finally {
            pm.close();
        }

        return counter;
    }
}
