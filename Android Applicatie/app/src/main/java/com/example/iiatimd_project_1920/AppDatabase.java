package com.example.iiatimd_project_1920;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Questions.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {


    private static AppDatabase INSTANCE;


    public abstract SignDAO questionDao();

    public static synchronized AppDatabase getInstance(final Context context){

        if (INSTANCE == null){
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    AppDatabase.class, "questions_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(RoomDBCallback)
                    .build();
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback RoomDBCallback = new RoomDatabase.Callback(){

        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(INSTANCE).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void,Void,Void>{

        private SignDAO questionDao;

        private PopulateDbAsyncTask(AppDatabase db){
            questionDao = db.questionDao();
        }

        @Override
        protected Void doInBackground(Void... voids){

            //Alle vragen van de applicatie met daarbij aangegeven wat het goede antwoord is.
            questionDao.insert(new Questions("Als het dimlicht op de auto niet werkt, mag je?","Niet rijden","Met stadslicht alsnog rijden","Rijden als het niet regend","Rijden voor 7 uur",1));
            questionDao.insert(new Questions("De druk in mijn autobanden(BAR) moet  zijn?","Plusminus 1,0 BAR","Plusminus 2,0 BAR","Plusminus 2,6 BAR","Plusminus 2,3 BAR",4));
            questionDao.insert(new Questions("Een voetganger bij een voetgangersoversteekplaats/zebrapad:","Moet je altijd voor laten gaan en een onbelemmerde voortgang verlenen","Laat je alleen voor gaan als hij of zij te kennen geeft over te willen steken.","Heeft geen voorrang","Heeft alleen voorrang als hij van rechts komt",1));
            questionDao.insert(new Questions("Lading op de auto mag aan de zijkant niet meer uitsteken dan:","Totaal van 1 meter","10cm per zijkant","20cm per zijkant","Tot een breedte van 2.50 meter",3));
            questionDao.insert(new Questions("Als de binnenspiegel ontbreekt, mag je:","Wel rijden als er een linker en rechter buitenspiegel aanwezig is.","Niet rijden want je ziet onvoldoende.","Alleen rijden als het licht is buiten","Alleen rijden in bebouwde kom",1));
            questionDao.insert(new Questions("Bij uitrijden op/verlaten van en autosnelweg:","Hoef je geen richting aangeven","Geef je richting aan daar waar de dubbele belijning van de uitrijstrook begint.","Geef je ruim van tevoren, op ongeveer 200 a 300 meter afstand richting aan.","Hoef je alleen bij uitrijden je richting aan te geven ",3));
            questionDao.insert(new Questions("Je bent 50 jaar en hebt 30 jaar je rijbewijs, je mag nu? ","Maximaal 0,5 promille alcohol in het bloed hebben.","Maximaal 0,2 promille alcohol in het bloed hebben."," Geen promille alcohol in het bloed hebben.","Maximaal 2 promille alcohol in het bloed hebben.",1));
            questionDao.insert(new Questions("Een pijl in het verkeerslicht bij afslaan betekent? ","Let op andere weggebruikers bij het afslaan en laat deze voor gaan.","Mag je niet afslaan ","Mag je alleen aflsaan bij groen licht","In principe hebben -als ik afsla- andere weggebruikers een rood verkeerslicht.",4));
            questionDao.insert(new Questions("Als je in een 30 km.-zone rijdt, dan wordt? ","Het bord slechts twee maal geplaatst.","Het verkeersbord voor ieder kruispunt herhaald.","Het bord alleen in de bebouwde kom herhaald","Het bord niet meer herhaald",1));
            questionDao.insert(new Questions("Als een verkeerslicht groen is moet je?","Even wat sneller gaan rijden om zodoende de doorstroming te bevorderen","Snelheid beetje minderen","Moet je meteen remmen","De maximaal toegestane snelheid aanhouden.",4));
            questionDao.insert(new Questions("Binnen een erf mag je maximaal rijden:?","stapvoets, 5 kilometer per uur","30 kilometer per uur","15 kilometer per uur","10 kilometer per uur",3));
            return null;
        }
    }
}

