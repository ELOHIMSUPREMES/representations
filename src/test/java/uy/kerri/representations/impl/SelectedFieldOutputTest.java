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

import org.hamcrest.CoreMatchers;
import org.hamcrest.junit.MatcherAssert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import uy.kerri.representations.exception.FieldNotMatchedException;
import uy.kerri.representations.impl.ArrayOfFields;
import uy.kerri.representations.impl.ArrayOfValues;
import uy.kerri.representations.impl.LabelledValue;
import uy.kerri.representations.impl.SelectedFieldOutput;
import uy.kerri.representations.impl.SelectedIndexOutput;

/**
 * Tests for {@link uy.kerri.representations.impl.SelectedFieldOutput}.
 *
 * @since 1.0
 */
public class SelectedFieldOutputTest {
    /**
     * Rule for expecting exceptions.
     */
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    /**
     * SelectedFieldOutput selects the field if it's a string.
     *
     * @throws Exception if anything goes wrong.
     */
    @Test
    public final void selectsAString() throws Exception {
        final String field = "name";
        final String value = "Lionel Messi";
        final Integer age = 31;
        final Double height = 1.71;
        final Long minutes = 3357L;
        final String team = "team";
        MatcherAssert.assertThat(
            "The correct string value wasn't selected.",
            new ArrayOfFields(
                new LabelledValue(field, value),
                new LabelledValue("age", age),
                new LabelledValue("height", height),
                new LabelledValue("minutes", minutes),
                new LabelledValue("active", true),
                new LabelledValue(
                    "pob",
                    new ArrayOfFields(
                        new LabelledValue("nation", "Argentina"),
                        new LabelledValue("city", "Rosario")
                    )
                ),
                new LabelledValue(
                    "teams",
                    new ArrayOfValues(
                        new LabelledValue(team, "Argentina National Team"),
                        new LabelledValue(team, "Barcelona")
                    )
                )
            ).printTo(new SelectedFieldOutput(field)).show(),
            CoreMatchers.equalTo(value)
        );
    }

    /**
     * SelectedFieldOutput selects the field if it's an integer.
     *
     * @throws Exception if anything goes wrong.
     */
    @Test
    public final void selectsAnInteger() throws Exception {
        final String field = "credits";
        final Integer value = 425;
        final Double average = 5.7;
        MatcherAssert.assertThat(
            "The correct integer value wasn't selected.",
            new ArrayOfFields(
                new LabelledValue("id", "1.234.567-8"),
                new LabelledValue(field, value),
                new LabelledValue("average", average)
            ).printTo(new SelectedFieldOutput(field)).show(),
            CoreMatchers.equalTo(value.toString())
        );
    }

    /**
     * SelectedSingleOutput selects the field if it's a boolean.
     *
     * @throws Exception if anything goes wrong.
     */
    @Test
    public final void selectsABoolean() throws Exception {
        final String field = "extinct";
        final Boolean value = false;
        MatcherAssert.assertThat(
            "The correct boolean value wasn't selected.",
            new ArrayOfFields(
                new LabelledValue("genus", "Hydrochoerus"),
                new LabelledValue("species", "H. Hydrochaeris"),
                new LabelledValue(field, value),
                new LabelledValue(
                    "predators",
                    new ArrayOfFields(
                        new LabelledValue("feline", "jaguar"),
                        new LabelledValue("bird", "eagle")
                    )
                )
            ).printTo(new SelectedFieldOutput(field)).show(),
            CoreMatchers.equalTo(value.toString())
        );
    }

    /**
     * SelectedFieldOutput selects the field if it's a double.
     *
     * @throws Exception if anything goes wrong.
     */
    @Test
    public final void selectsADouble() throws Exception {
        final String field = "densityAtSTP";
        final Double value = 0.08988;
        final Double fusion = 0.117;
        MatcherAssert.assertThat(
            "The correct double value wasn't selected.",
            new ArrayOfFields(
                new LabelledValue("symbol", "H"),
                new LabelledValue("atomicNumber", 1),
                new LabelledValue(field, value),
                new LabelledValue("heatOfFusion", fusion)
            ).printTo(new SelectedFieldOutput(field)).show(),
            CoreMatchers.equalTo(value.toString())
        );
    }

    /**
     * SelectedFieldOutput selects the field if it's a long.
     *
     * @throws Exception if anything goes wrong.
     */
    @Test
    public final void selectsALong() throws Exception {
        final String field = "maxNumberOfFiles";
        final Long value = 4294967295L;
        final Integer introduced = 1997;
        MatcherAssert.assertThat(
            "The correct long value wasn't selected.",
            new ArrayOfFields(
                new LabelledValue("introduced", introduced),
                new LabelledValue("developer", "Microsoft"),
                new LabelledValue("acronym", "NTFS"),
                new LabelledValue(field, value)
            ).printTo(new SelectedFieldOutput(field)).show(),
            CoreMatchers.equalTo(value.toString())
        );
    }

    /**
     * SelectedFieldOutput allows to select a nested field.
     *
     * @throws Exception if anything goes wrong.
     */
    @Test
    public final void selectsANestedField() throws Exception {
        final String composite = "keyPeople";
        final String nested = "director";
        final String value = "Jack P. Dorsey";
        final Integer employees = 3372;
        MatcherAssert.assertThat(
            "The correct nested value wasn't selected.",
            new ArrayOfFields(
                new LabelledValue("website", "twitter.com"),
                new LabelledValue(
                    composite,
                    new ArrayOfFields(
                        new LabelledValue("chairman", "Omid R. Kordestani"),
                        new LabelledValue(nested, value),
                        new LabelledValue("cfo", "Ned Segal")
                    )
                ),
                new LabelledValue(
                    "headquarters",
                    new ArrayOfFields(
                        new LabelledValue("district", "San Francisco"),
                        new LabelledValue("state", "California"),
                        new LabelledValue("country", "United States")
                    )
                ),
                new LabelledValue("employees", employees)
            ).printTo(
                new SelectedFieldOutput(
                    composite,
                    new SelectedFieldOutput(nested)
                )
            ).show(),
            CoreMatchers.equalTo(value)
        );
    }

    /**
     * SelectedFieldOutput allows to select a sequence of values in a field.
     *
     * @throws Exception if anything goes wrong.
     */
    @Test
    public final void selectsASequenceOfValues() throws Exception {
        final String field = "transactions";
        final Integer value = 1001;
        final Integer first = 1000;
        final Integer third = 1002;
        MatcherAssert.assertThat(
            "The correct value of the sequence wasn't selected.",
            new ArrayOfFields(
                new LabelledValue("account", "199992-20001"),
                new LabelledValue(
                    field,
                    new ArrayOfValues(
                        new LabelledValue("2018-01-02", first),
                        new LabelledValue("2018-02-02", value),
                        new LabelledValue("2018-03-02", third)
                    )
                ),
                new LabelledValue("owner", "Lalo Landa")
            ).printTo(
                new SelectedFieldOutput(
                    field,
                    new SelectedIndexOutput(2)
                )
            ).show(),
            CoreMatchers.equalTo(value.toString())
        );
    }

    /**
     * SelectedFieldOutput throws FieldNotMatchedException if the
     *  field isn't present.
     *
     * @throws Exception if anything goes wrong.
     */
    @Test
    public final void throwsIfSingleNotPresent() throws Exception {
        final String field = "single";
        this.thrown.expect(FieldNotMatchedException.class);
        this.thrown.expectMessage(
            CoreMatchers.equalTo(
                String.format("The field \"%s\" is not present.", field)
            )
        );
        new ArrayOfFields(
            new LabelledValue("this", "is"),
            new LabelledValue(
                "my",
                new ArrayOfFields(
                    new LabelledValue("try", 2),
                    new LabelledValue("go", 2.0),
                    new LabelledValue("bed", "now")
                )
            )
        ).printTo(new SelectedFieldOutput(field)).show();
    }
}