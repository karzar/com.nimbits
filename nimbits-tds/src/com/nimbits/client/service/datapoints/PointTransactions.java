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

package com.nimbits.client.service.datapoints;

import com.nimbits.client.exception.*;
import com.nimbits.client.model.email.*;
import com.nimbits.client.model.entity.*;
import com.nimbits.client.model.point.*;

import javax.servlet.http.*;
import java.util.*;

/**
 * Created by bsautner
 * User: benjamin
 * Date: 9/30/11
 * Time: 2:12 PM
 */
public interface PointTransactions {

    List<Point> getPoints() throws NimbitsException;

    Point getPointByID(final long id) throws NimbitsException;

    Point updatePoint(final Point point) throws NimbitsException;

    Point getPointByName(final EntityName name) throws NimbitsException;

    void deletePoint(final Point p) throws NimbitsException;

    Point checkPoint(final HttpServletRequest req, final EmailAddress email, final Point point) throws NimbitsException;

    List<Point> getAllPoints(int start, int end);

    List<Point> getIdlePoints();

    Point getPointByUUID(final String uuid);

    List<Point> getAllPoints();

    Point addPoint(Entity entity);

    Point addPoint(Entity entity, Point point);

    List<Point>  getPoints(List<Entity> entities);
}
