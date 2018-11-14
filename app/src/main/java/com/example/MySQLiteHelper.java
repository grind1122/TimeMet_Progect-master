package com.example;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MySQLiteHelper extends SQLiteOpenHelper {
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "db_price_list";

    public MySQLiteHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE PRICE_LIST (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "NAME TEXT, " +
                    "DESCRIPTION TEXT, " +
                    "PRICE INTEGER, " +
                    "IMAGE_RESOURCES_ID INTEGER);");

        insertInfo(db, "Лом черных металлов 3А", "Кусковые лом и от-ходы и стальной скрап, удобные для погрузки и рагрузки. Не допускается про-волока и изделия из проволоки.Габарит 1500 х 500 х 500 мм. Толщина металла не менее 4 мм. Масса куска не менее 2 кг.",17200, R.drawable.a_3);
        insertInfo(db,"Лом черных металлов 3АЖД","Рельсовый лом длиной до 1,5 метров, диски и оси колесных пар в габарите 1500х500х500 мм. Рельсовые скрепления: накладки, подкладки, костыли, болты.", 17500,R.drawable.a_3_gd);
        insertInfo(db,"Лом черных металлов 3А2","Кусковые лом и отходы, пакеты из чистых легковесных стальных отходов. Не допускаются не разобранные узлы, агрегаты и изделия в сборе. Габарит 1500х500х500мм. Толщина металла не менее 4мм. Масса куска не менее 1кг. Трубы должны иметь наружный диаметр не более 150мм.", 16900, R.drawable.a_3_2);
        insertInfo(db,"Лом черных металлов 5А","Негабаритный углеродистый кусковой лом. Толщина металла не менее 6 мм. Масса куска не более 2 тонн (масса куска свыше 2 т. по согласованию). Допускается труба диаметром до 800 мм. С большим диаметром по согласованию.",16700,R.drawable.a_5);
        insertInfo(db,"Лом 5АМ","Стальной углеродистый лом и отходы. Габариты не регламентируются. Масса куска не менее 2 кг и не более 5 тонн (свыше 5 тонн по согласованию). Толщина стенки не более 250 мм.",16400, R.drawable.a_5_m);
        insertInfo(db,"Лом черных металлов 12А","Стальные листовые, полосовые и сортовые, легковесный промыш-ленный и бытовой лом, металлоконструкции, трубы без асбеста и би-тума.Толщина стенки менее 6 мм. Минимальные линейные размеры должны быть не менее 100х100х100мм. Максимальные линейные размеры не должны превышать 3500 х 2500 х 1000 мм.",14500,R.drawable.a_12);
        insertInfo(db,"Автомобильный лом 12А1","Пакеты оцинкован-ные, луженные. Масса пакетов должна быть не менее 40 кг.",10000,R.drawable.a_12_1);
        insertInfo(db,"Лом черных металлов 16А", "16А - марка лома черных металлов, имеющая форму вьюнообразной стружки. Подразумевается последующая переплавка в печах. Нет ограничения по весу.", 10000, R.drawable.a_16);
        insertInfo(db,"Лом черных металлов 17А","Куски машинных чугунных отливок, а также чушки вторичного литейного чугуна. Максимальный размер куска должен быть не более 300 мм, а остальные размеры должны соответствовать размерам куска массой не более 20 кг, но не менее 0,5 кг. Куски массой менее 0,5 кг допускаются в количестве не более 2% от массы партии.",13000,R.drawable.a_17);
        insertInfo(db, "Лом черных металлов 19А, сантехнический","Отходы при производстве чугуна №3 с повышенным содержанием фтора.",11000,R.drawable.a_19);
        insertInfo(db,"Лом черных металлов 20А","Машинные чугунные отливки., чугунные станки, радиаторы, трубы. Габариты не регламентируются. Масса куска не менее 2 кг. и не более 2 тонн (свыше 2 т по согласованию).",10500, R.drawable.a_20);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    private static void insertInfo(SQLiteDatabase db, String name, String desc, int price, int image_resources){
        ContentValues contentValues = new ContentValues();
        contentValues.put("NAME", name);
        contentValues.put("DESCRIPTION", desc);
        contentValues.put("PRICE", price);
        contentValues.put("IMAGE_RESOURCES_ID", image_resources);
        db.insert("PRICE_LIST", null, contentValues);
    }

    public static int getDbVersion() {
        return DB_VERSION;
    }

    public static String getDbName() {
        return DB_NAME;
    }
}
