package org.bboed.students.ahmedmoustafa18;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Ahmed on 3/11/2017.
 */
public class BagTest {
    Bag bag;

    @Before
    public void setUp() throws Exception {
        bag = new Bag(1);
    }

    @Test
    public void getPositionFromId() throws Exception {
        assertEquals(Game.BAG_MARGIN_X, bag.getPositionFromId(1).getX());
    }

}