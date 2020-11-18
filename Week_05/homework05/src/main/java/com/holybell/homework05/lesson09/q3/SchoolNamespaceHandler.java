/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.holybell.homework05.lesson09.q3;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.springframework.beans.factory.xml.NamespaceHandlerSupport;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.util.StringUtils;
import org.w3c.dom.Element;

public class SchoolNamespaceHandler extends NamespaceHandlerSupport {

    @Override
    public void init() {
        // 将 "school" 元素注册对应的 BeanDefinitionParser 实现
        registerBeanDefinitionParser("qinghua", new UserBeanDefinitionParser());
    }

    static class UserBeanDefinitionParser extends AbstractSingleBeanDefinitionParser {

        // 构造的对象类
        @Override
        protected Class<?> getBeanClass(Element element) {
            return School.class;
        }

        // 从Document的Element元素属性解析值，构造BeanDefinition
        @Override
        protected void doParse(Element element, ParserContext parserContext, BeanDefinitionBuilder builder) {
            setPropertyValue("id", element, builder);
            setPropertyValue("name", element, builder);
            setPropertyValue("totalStudents", element, builder);
        }

        // 构造BeanDefinition
        private void setPropertyValue(String attributeName, Element element, BeanDefinitionBuilder builder) {
            String attributeValue = element.getAttribute(attributeName);
            if (StringUtils.hasText(attributeValue)) {
                builder.addPropertyValue(attributeName, attributeValue); // -> <property name="" value=""/>
            }
        }
    }
}
