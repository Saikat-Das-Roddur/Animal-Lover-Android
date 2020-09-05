package org.richit.animal.Models;

import android.os.Parcel;
import android.os.Parcelable;

public class AnimalModel implements Parcelable {
    @SuppressWarnings("unused")
    public static final Parcelable.Creator<AnimalModel> CREATOR = new Parcelable.Creator<AnimalModel>() {
        @Override
        public AnimalModel createFromParcel(Parcel in) {
            return new AnimalModel(in);
        }

        @Override
        public AnimalModel[] newArray(int size) {
            return new AnimalModel[size];
        }
    };
    String name;
    String kingdom;
    String phylum;
    String animalClass;
    String order;
    String family;
    String genus;
    String numberofspecies;
    String location;
    String habitat;
    String diet;
    String prey;
    String predators;
    String lifestyle;
    String funfact;
    String color;
    String food;
    String scientific;
    String image;
    String map;
    String desc1;
    String desc2;
    String desc3;
    String desc4;
    String skin;
    String speed;
    String water;
    String phlevel;
    String conservation;
    String wingspan;
    String features;
    String specialfeature;
    String commonname ;
    String temperament;
    String training;
    String lifespan;
    String weight;
    String distinctivefeature;
    String mostdistinctivefeature;
    String type;
    String origin;
    String mainprey;

    public String getSpecialfeature() {
        return specialfeature;
    }

    public void setSpecialfeature(String specialfeature) {
        this.specialfeature = specialfeature;
    }

    public String getCommonname() {
        return commonname;
    }

    public void setCommonname(String commonname) {
        this.commonname = commonname;
    }

    public String getTemperament() {
        return temperament;
    }

    public void setTemperament(String temperament) {
        this.temperament = temperament;
    }

    public String getTraining() {
        return training;
    }

    public void setTraining(String training) {
        this.training = training;
    }

    public String getLifespan() {
        return lifespan;
    }

    public void setLifespan(String lifespan) {
        this.lifespan = lifespan;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getDistinctivefeature() {
        return distinctivefeature;
    }

    public void setDistinctivefeature(String distinctivefeature) {
        this.distinctivefeature = distinctivefeature;
    }

    public String getMostdistinctivefeature() {
        return mostdistinctivefeature;
    }

    public void setMostdistinctivefeature(String mostdistinctivefeature) {
        this.mostdistinctivefeature = mostdistinctivefeature;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getMainprey() {
        return mainprey;
    }

    public void setMainprey(String mainprey) {
        this.mainprey = mainprey;
    }

    public String getFeatures() {
        return features;
    }

    public void setFeatures(String features) {
        this.features = features;
    }

    protected AnimalModel(Parcel in) {
        name = in.readString();
        image = in.readString();
        scientific = in.readString();
        animalClass = in.readString();
        map = in.readString();
        location = in.readString();
        habitat = in.readString();
        diet = in.readString();
        prey = in.readString();
        predators = in.readString();
        lifestyle = in.readString();
        funfact = in.readString();
        color = in.readString();
        food = in.readString();
        desc1 = in.readString();
        desc2 = in.readString();
        desc3 = in.readString();
        desc4 = in.readString();
        skin = in.readString();
        speed = in.readString();
        water = in.readString();
        phlevel = in.readString();
        conservation = in.readString();
        wingspan = in.readString();
        features = in.readString();

        kingdom = in.readString();
        phylum = in.readString();
        order = in.readString();
        family = in.readString();
        genus = in.readString();
        numberofspecies = in.readString();

        specialfeature = in.readString();
        commonname  = in.readString();
        temperament = in.readString();
        training = in.readString();
        lifespan = in.readString();
        weight = in.readString();
        distinctivefeature = in.readString();
        mostdistinctivefeature = in.readString();
        type = in.readString();
        origin = in.readString();
        mainprey = in.readString();


    }


    public String getKingdom() {
        return kingdom;
    }

    public void setKingdom(String kingdom) {
        this.kingdom = kingdom;
    }

    public String getPhylum() {
        return phylum;
    }

    public void setPhylum(String phylum) {
        this.phylum = phylum;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public String getGenus() {
        return genus;
    }

    public void setGenus(String genus) {
        this.genus = genus;
    }

    public String getNumberofspecies() {
        return numberofspecies;
    }

    public void setNumberofspecies(String numberofspecies) {
        this.numberofspecies = numberofspecies;
    }

    public String getSkin() {
        return skin;
    }

    public void setSkin(String skin) {
        this.skin = skin;
    }

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    public String getWater() {
        return water;
    }

    public void setWater(String water) {
        this.water = water;
    }

    public String getPhlevel() {
        return phlevel;
    }

    public void setPhlevel(String phlevel) {
        this.phlevel = phlevel;
    }

    public String getConservation() {
        return conservation;
    }

    public void setConservation(String conservation) {
        this.conservation = conservation;
    }

    public String getWingspan() {
        return wingspan;
    }

    public void setWingspan(String wingspan) {
        this.wingspan = wingspan;
    }

    public String getDesc1() {
        return desc1;
    }

    public void setDesc1(String desc1) {
        this.desc1 = desc1;
    }

    public String getDesc2() {
        return desc2;
    }

    public void setDesc2(String desc2) {
        this.desc2 = desc2;
    }

    public String getDesc3() {
        return desc3;
    }

    public void setDesc3(String desc3) {
        this.desc3 = desc3;
    }


    public String getDesc4() {
        return desc4;
    }

    public void setDesc4(String desc4) {
        this.desc4 = desc4;
    }

    public String getScientific() {
        return scientific;
    }

    public void setScientific(String scientific) {
        this.scientific = scientific;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAnimalClass() {
        return animalClass;
    }

    public void setAnimalClass(String animalClass) {
        this.animalClass = animalClass;
    }

    public String getMap() {
        return map;
    }

    public void setMap(String map) {
        this.map = map;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getHabitat() {
        return habitat;
    }

    public void setHabitat(String habitat) {
        this.habitat = habitat;
    }

    public String getDiet() {
        return diet;
    }

    public void setDiet(String diet) {
        this.diet = diet;
    }

    public String getPrey() {
        return prey;
    }

    public void setPrey(String prey) {
        this.prey = prey;
    }

    public String getPredators() {
        return predators;
    }

    public void setPredators(String predators) {
        this.predators = predators;
    }

    public String getLifestyle() {
        return lifestyle;
    }

    public void setLifestyle(String lifestyle) {
        this.lifestyle = lifestyle;
    }

    public String getFunfact() {
        return funfact;
    }

    public void setFunfact(String funfact) {
        this.funfact = funfact;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getFood() {
        return food;
    }

    public void setFood(String food) {
        this.food = food;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(image);
        dest.writeString(scientific);
        dest.writeString(animalClass);
        dest.writeString(map);
        dest.writeString(location);
        dest.writeString(habitat);
        dest.writeString(diet);
        dest.writeString(prey);
        dest.writeString(predators);
        dest.writeString(lifestyle);
        dest.writeString(funfact);
        dest.writeString(color);
        dest.writeString(food);
        dest.writeString(desc1);
        dest.writeString(desc2);
        dest.writeString(desc3);
        dest.writeString(desc4);
        dest.writeString(skin);
        dest.writeString(speed);
        dest.writeString(water);
        dest.writeString(phlevel);
        dest.writeString(conservation);
        dest.writeString(wingspan);
        dest.writeString(features);
        dest.writeString(kingdom);
        dest.writeString(phylum);
        dest.writeString(order);
        dest.writeString(family);
        dest.writeString(genus);
        dest.writeString(numberofspecies);

        dest.writeString(specialfeature);
        dest.writeString(commonname );
        dest.writeString(temperament);
        dest.writeString(training);
        dest.writeString(lifespan);
        dest.writeString(weight);
        dest.writeString(distinctivefeature);
        dest.writeString(mostdistinctivefeature);
        dest.writeString(type);
        dest.writeString(origin);
        dest.writeString(mainprey);


    }
}