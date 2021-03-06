/*
 * Copyright (C) 2005-2013 ManyDesigns srl.  All rights reserved.
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

package com.manydesigns.elements.test;

import com.manydesigns.elements.ElementsProperties;
import com.manydesigns.elements.ElementsThreadLocals;
import com.manydesigns.elements.servlet.MutableHttpServletRequest;
import com.manydesigns.elements.servlet.MutableServletContext;
import com.manydesigns.elements.xml.XmlBuffer;
import org.apache.commons.configuration2.Configuration;

import java.util.Locale;

/*
* @author Paolo Predonzani     - paolo.predonzani@manydesigns.com
* @author Angelo Lupo          - angelo.lupo@manydesigns.com
* @author Giampiero Granatella - giampiero.granatella@manydesigns.com
* @author Alessio Stalla       - alessio.stalla@manydesigns.com
*/
public abstract class ElementsTestSupport {
    public static final String copyright =
            "Copyright (c) 2005-2019, ManyDesigns srl";

    public Configuration elementsConfiguration;

    public MutableServletContext servletContext;
    public MutableHttpServletRequest req;

    public void setUp() throws Exception {
        XmlBuffer.checkWellFormed = true;

        setUpProperties();
        setUpSingletons();
        setUpRequest();
        setUpElementsThreadLocals();
    }

    public void setUpProperties() {
        elementsConfiguration = ElementsProperties.getConfiguration();
    }

    public void setUpSingletons() {
        servletContext = new MutableServletContext();
    }

    public void setUpRequest() {
        req = new MutableHttpServletRequest(servletContext);
        req.setContextPath("");
        req.setRequestURI("");
        //Uniform locales
        req.locales.clear();
        req.locales.add(Locale.forLanguageTag("en"));
        req.locales.add(Locale.forLanguageTag("it"));
    }

    public void setUpElementsThreadLocals() {
        //Force English so tests don't depend on the environment
        Locale.setDefault(Locale.ENGLISH);
        ElementsThreadLocals.setupDefaultElementsContext();
        ElementsThreadLocals.setHttpServletRequest(req);
        ElementsThreadLocals.setServletContext(servletContext);
    }

    public void tearDown() throws Exception {
        ElementsThreadLocals.removeElementsContext();
    }

}
