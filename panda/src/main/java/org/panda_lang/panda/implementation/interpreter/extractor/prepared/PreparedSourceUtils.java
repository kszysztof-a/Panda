/*
 * Copyright (c) 2015-2016 Dzikoysk
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.panda_lang.panda.implementation.interpreter.extractor.prepared;

import org.panda_lang.framework.interpreter.lexer.TokenRepresentation;
import org.panda_lang.framework.interpreter.token.Token;
import org.panda_lang.framework.interpreter.token.TokenUtils;
import org.panda_lang.panda.implementation.interpreter.extractor.TokenPatternUnit;

/**
 * Utils for {@link org.panda_lang.framework.interpreter.lexer.TokenizedSource} based on {@link PreparedSource}
 */
public class PreparedSourceUtils {

    public static int countHardTypedUnits(TokenPatternUnit[] units) {
        int i = 0;

        for (TokenPatternUnit unit : units) {
            if (unit.isGap()) {
                continue;
            }

            ++i;
        }

        return i;
    }

    /**
     * @return index of the specified token in array, returns -1 if the token was not found
     */
    public static int indexOf(PreparedSource preparedSource, Token search) {
        PreparedRepresentation[] representations = preparedSource.getPreparedRepresentations();

        for (int i = 0; i < representations.length; i++) {
            PreparedRepresentation representation = representations[i];

            if (representation.getNestingLevel() > 0) {
                continue;
            }

            Token token = representation.getTokenRepresentation().getToken();

            if (search.equals(token)) {
                return i;
            }
        }

        return -1;
    }

    /**
     * @return last index of the specified token in array, returns -1 if the token was not found
     */
    public static int lastIndexOf(PreparedSource preparedSource, Token search) {
        PreparedRepresentation[] representations = preparedSource.getPreparedRepresentations();

        for (int i = representations.length - 1; i > -1; i--) {
            PreparedRepresentation representation = representations[i];

            if (representation.getNestingLevel() > 0) {
                continue;
            }

            TokenRepresentation tokenRepresentation = representation.getTokenRepresentation();

            if (TokenUtils.equals(tokenRepresentation, search)) {
                return i;
            }
        }

        return -1;
    }

}
