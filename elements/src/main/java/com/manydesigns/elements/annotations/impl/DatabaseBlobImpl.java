/*
 * Copyright (C) 2005-2015 ManyDesigns srl.  All rights reserved.
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

package com.manydesigns.elements.annotations.impl;

import com.manydesigns.elements.annotations.DatabaseBlob;

import java.lang.annotation.Annotation;

/*
* @author Paolo Predonzani     - paolo.predonzani@manydesigns.com
* @author Angelo Lupo          - angelo.lupo@manydesigns.com
* @author Giampiero Granatella - giampiero.granatella@manydesigns.com
* @author Alessio Stalla       - alessio.stalla@manydesigns.com
*/
@SuppressWarnings({"ClassExplicitlyAnnotation"})
public class DatabaseBlobImpl implements DatabaseBlob {
    public static final String copyright =
            "Copyright (c) 2005-2015, ManyDesigns srl";

    private final String contentTypeProperty;
    private final String fileNameProperty;
    private final String timestampProperty;

    public DatabaseBlobImpl(String contentTypeProperty, String fileNameProperty, String timestampProperty) {
        this.contentTypeProperty = contentTypeProperty;
        this.fileNameProperty = fileNameProperty;
        this.timestampProperty = timestampProperty;
    }

    public Class<? extends Annotation> annotationType() {
        return DatabaseBlob.class;
    }

    @Override
    public String contentTypeProperty() {
        return contentTypeProperty;
    }

    @Override
    public String fileNameProperty() {
        return fileNameProperty;
    }

    @Override
    public String timestampProperty() {
        return timestampProperty;
    }
}