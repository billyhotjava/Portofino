/*
 * Copyright (C) 2005-2020 ManyDesigns srl.  All rights reserved.
 * http://www.manydesigns.com/
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 3 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */

package com.manydesigns.portofino.modules;

import com.manydesigns.portofino.PortofinoProperties;
import com.manydesigns.portofino.database.platforms.GoogleCloudSQLDatabasePlatform;
import com.manydesigns.portofino.liquibase.databases.GoogleCloudSQLDatabase;
import com.manydesigns.portofino.liquibase.sqlgenerators.GoogleCloudSQLLockDatabaseChangeLogGenerator;
import com.manydesigns.portofino.model.database.platforms.DatabasePlatformsRegistry;
import liquibase.database.DatabaseFactory;
import liquibase.sqlgenerator.SqlGeneratorFactory;
import org.apache.commons.configuration2.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/*
* @author Paolo Predonzani     - paolo.predonzani@manydesigns.com
* @author Angelo Lupo          - angelo.lupo@manydesigns.com
* @author Giampiero Granatella - giampiero.granatella@manydesigns.com
* @author Alessio Stalla       - alessio.stalla@manydesigns.com
*/
public class GooglecloudsqlModule implements Module {
    public static final String copyright =
            "Copyright (C) 2005-2020 ManyDesigns srl";

    //**************************************************************************
    // Fields
    //**************************************************************************

    @Autowired
    public Configuration configuration;

    @Autowired
    DatabasePlatformsRegistry databasePlatformsRegistry;

    protected ModuleStatus status = ModuleStatus.CREATED;

    //**************************************************************************
    // Logging
    //**************************************************************************

    public static final Logger logger =
            LoggerFactory.getLogger(GooglecloudsqlModule.class);

    @Override
    public String getModuleVersion() {
        return PortofinoProperties.getPortofinoVersion();
    }

    @Override
    public String getName() {
        return "Google Cloud SQL";
    }

    @PostConstruct
    public void init() {
        if(configuration.getBoolean(DatabaseModule.LIQUIBASE_ENABLED, true)) {
            logger.debug("Registering Google Cloud SQL");
            DatabaseFactory.getInstance().register(new GoogleCloudSQLDatabase());
            logger.debug("Registering GoogleCloudSQLLockDatabaseChangeLogGenerator");
            SqlGeneratorFactory.getInstance().register(
                    new GoogleCloudSQLLockDatabaseChangeLogGenerator());
        }
        databasePlatformsRegistry.addDatabasePlatform(new GoogleCloudSQLDatabasePlatform());
        status = ModuleStatus.STARTED;
    }

    @PreDestroy
    public void destroy() {
        status = ModuleStatus.DESTROYED;
    }

    @Override
    public ModuleStatus getStatus() {
        return status;
    }
}
