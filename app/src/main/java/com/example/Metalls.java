//package com.example;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class Metalls {
//   private String name;
//   private String cost;
//   private int imageResources;
//   private String description;
//
//    public String getName() {
//        return name;
//    }
//
//    public String getCost() {
//        return cost;
//    }
//
//    public String getDescription() {
//        return description;
//    }
//
//    public int getImageResources() {
//        return imageResources;
//    }
//
//    public Metalls(String name, String cost, String description, int imageResources) {
//        this.name = name;
//        this.cost = cost;
//        this.description = description;
//        this.imageResources = imageResources;
//    }
//    public static List<Metalls> getMetallsForCardView(){
//        List<Metalls> list = new ArrayList<>();
//        list.add(new Metalls("Категория металла - 3A", "От 17500 руб/тонна...",
//                "3А - исключительно стальной лом. Максимальные размеры 1,6х0,5х0,6 м. Масса куска должна находится в диапазоне от 1 до 600 кг. Также к этой категории относятся трубы диаметром не более 150 мм со стенкой от 4 мм. Трубы необходимо разрезать вдоль и сплющить.", R.drawable.a_3));
//        list.add(new Metalls("Категория металла - 5A","От 17200 руб/тонна...",
//                "5А, 5Б - соответственно стальной и легированный лом. Негабаритные куски массой до 5 кг. Включает в себя стальной скрап.",R.drawable.a_5));
//        list.add(new Metalls("Категория металла - 12A", "От 16800 руб/тонна...",
//                "12А - бытовой и промышленный лом. Фасуется в пакеты массой не более 40 кг. Нет ограничения по габаритам.",R.drawable.a_12));
//        list.add(new Metalls("Категория металла - 16A", "От 14000 руб/тонна...",
//                "16А - марка лома черных металлов, имеющая форму вьюнообразной стружки. Подразумевается последующая переплавка в печах. Нет ограничения по весу.", R.drawable.a_16));
//        return list;
//    }
//}
