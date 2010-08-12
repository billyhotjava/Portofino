/*
 * Copyright (C) 2005-2010 ManyDesigns srl.  All rights reserved.
 * http://www.manydesigns.com/
 *
 * Unless you have purchased a commercial license agreement from ManyDesigns srl,
 * the following license terms apply:
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License version 3 as published by
 * the Free Software Foundation.
 *
 * There are special exceptions to the terms and conditions of the GPL
 * as it is applied to this software. View the full text of the
 * exception in file OPEN-SOURCE-LICENSE.txt in the directory of this
 * software distribution.
 *
 * This program is distributed WITHOUT ANY WARRANTY; and without the
 * implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, see http://www.gnu.org/licenses/gpl.txt
 * or write to:
 * Free Software Foundation, Inc.,
 * 59 Temple Place - Suite 330,
 * Boston, MA  02111-1307  USA
 *
 */

package com.manydesigns.elements.text;

import com.manydesigns.elements.reflection.ClassAccessor;
import com.manydesigns.elements.reflection.JavaClassAccessor;
import com.manydesigns.elements.reflection.PropertyAccessor;
import org.apache.commons.beanutils.ConvertUtils;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
* @author Paolo Predonzani     - paolo.predonzani@manydesigns.com
* @author Angelo Lupo          - angelo.lupo@manydesigns.com
* @author Giampiero Granatella - giampiero.granatella@manydesigns.com
*/
public class ExpressionGenerator implements Generator {
    public static final String copyright =
            "Copyright (c) 2005-2010, ManyDesigns srl";

    //**************************************************************************
    // Fields
    //**************************************************************************

    public final Pattern pattern = Pattern.compile("\\{[^\\}]*\\}");

    protected final String parsedExpression;
    protected final List<PropertyAccessor> propertyAccessors;


    //**************************************************************************
    // Constructors
    //**************************************************************************

    public ExpressionGenerator(Class clazz, String expression) {
        this(new JavaClassAccessor(clazz), expression);
    }

    public ExpressionGenerator(ClassAccessor classAccessor, String expression) {
        propertyAccessors = new ArrayList<PropertyAccessor>();
        parsedExpression =
                parseExpression(classAccessor, expression, propertyAccessors);
    }

    protected String parseExpression(ClassAccessor classAccessor,
                                   String expression,
                                   List<PropertyAccessor> propertyAccessors) {
        Matcher m = pattern.matcher(expression);
        int previousEnd = 0;
        StringBuilder sb = new StringBuilder();
        int index = 0;
        while (m.find()) {
            int start = m.start();
            sb.append(expression.substring(previousEnd, start));
            sb.append("{");
            sb.append(index);
            sb.append("}");
            int end = m.end();
            String group = m.group();
            String name = group.substring(1, group.length()-1);
            PropertyAccessor propertyAccessor;
            try {
                propertyAccessor = classAccessor.getProperty(name);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
                return null;
            }
            propertyAccessors.add(propertyAccessor);
            previousEnd = end;
            index++;
        }
        sb.append(expression.substring(previousEnd, expression.length()));
        return sb.toString();
    }


    //**************************************************************************
    // Generator implementation
    //**************************************************************************

    public String generate(Object obj) {
        if (obj == null) {
            return null;
        }
        try {
            String[] args = new String[propertyAccessors.size()];
            for (int i = 0; i < args.length; i++) {
                Object value = propertyAccessors.get(i).get(obj);
                String stringValue = ConvertUtils.convert(value);
                args[i] = stringValue;
            }
            return MessageFormat.format(parsedExpression, args);
        } catch (Throwable e) {
            return null;
        }
    }

}
