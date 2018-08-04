/*
 * MIT License
 *
 * Copyright (c) 2018 Pablo Da Costa Porto
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package uy.kerri.representations.test.impl;

import org.apache.commons.lang3.StringUtils;
import org.hamcrest.CoreMatchers;
import org.hamcrest.junit.MatcherAssert;
import org.junit.Test;
import uy.kerri.representations.fake.FakeOutput;
import uy.kerri.representations.impl.BooleanField;
import uy.kerri.representations.impl.FieldsRepresentation;
import uy.kerri.representations.impl.IntegerField;
import uy.kerri.representations.impl.StringField;

/**
 * Tests for {@link uy.kerri.representations.impl.FieldsRepresentation}.
 *
 * @since 1.0
 */
public class FieldsRepresentationTest {
    /**
     * FieldsRepresentation prints nothing if it has no fields.
     *
     * @throws Exception if something fails.
     */
    @Test
    public final void printsNothingIfEmpty() throws Exception {
        final String name = "name:String:William";
        final FakeOutput output = new FakeOutput(name);
        MatcherAssert.assertThat(
            "Empty output isn't empty",
            new FieldsRepresentation().print(output).show(),
            CoreMatchers.equalTo(name)
        );
    }

    /**
     * FieldsRepresentation prints its fields on the output.
     *
     * @throws Exception if something fails.
     */
    @Test
    public final void printsFields() throws Exception {
        final String name = "name:String:Daniel";
        final String surname = "Umpiérrez";
        final Integer age = 43;
        final Boolean registered = false;
        final FakeOutput output = new FakeOutput(name);
        MatcherAssert.assertThat(
            "Fields weren't printed correctly.",
            new FieldsRepresentation(
                new StringField("surname", surname),
                new IntegerField("age", age),
                new BooleanField("registered", registered)
            ).print(output).show(),
            CoreMatchers.equalTo(
                StringUtils.join(
                    new String[] {
                        name,
                        String.format("surname:String:%s", surname),
                        String.format("age:Integer:%d", age),
                        String.format("registered:Boolean:%b", registered),
                    },
                    String.format("%n")
                )
            )
        );
    }
}
