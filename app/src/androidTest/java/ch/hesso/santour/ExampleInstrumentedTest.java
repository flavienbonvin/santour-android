package ch.hesso.santour;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import ch.hesso.santour.db.DBCallback;
import ch.hesso.santour.db.TrackDB;
import ch.hesso.santour.model.POD;
import ch.hesso.santour.model.POI;
import ch.hesso.santour.model.Position;
import ch.hesso.santour.model.RatePOD;
import ch.hesso.santour.model.Track;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void AddTrackSimple() throws Exception {

        final Track t = new Track("Track Test");
        TrackDB.add(t, new DBCallback() {
            @Override
            public void resolve(Object o) {
                Track res = (Track) o;
                assertEquals(t.name, res.name);
            }
        });
    }
}
