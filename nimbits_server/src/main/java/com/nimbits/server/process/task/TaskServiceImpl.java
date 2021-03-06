/*
 * Copyright (c) 2013 Nimbits Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS,  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either expressed or implied.  See the License for the specific language governing permissions and limitations under the License.
 */

package com.nimbits.server.process.task;


import com.nimbits.client.exception.ValueException;
import com.nimbits.client.model.entity.Entity;
import com.nimbits.client.model.point.Point;
import com.nimbits.client.model.timespan.Timespan;
import com.nimbits.client.model.user.User;
import com.nimbits.client.model.value.Value;
import com.nimbits.server.transaction.entity.dao.EntityDao;
import com.nimbits.server.transaction.entity.service.EntityService;
import com.nimbits.server.transaction.value.service.ValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {



    @Autowired
    private ValueTask valueTask;



    public TaskServiceImpl() {

    }



    @Override
    public void startDataDumpTask(User user, Entity entity, Timespan timespan) {

    }

    @Override
    public void startUploadTask(User user, Point entity, String blobKey) {

    }


    @Override
    public void startIncomingMailTask(String fromAddress, String inContent) {

    }



    @Override
    public void startPointTask(long pos) {

    }

    @Override
    public void startPointTask(String cursor) {

    }



    @Override
    public void startRecordValueTask(final User user, final Point entity, final Value value, final boolean preAuthorised) {

        try {
            valueTask.recordValue(value, user, entity, preAuthorised);
        } catch (ValueException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
