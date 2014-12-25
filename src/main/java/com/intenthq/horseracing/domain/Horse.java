package com.intenthq.horseracing.domain;

public class Horse {
    private String name;
    private Integer lane;
    private Integer distanceCovered = 0;
    private Integer position = 0;

    public Horse(String name, Integer lane) {
        this.name = name;
        this.lane = lane;
    }

    public void run(int yards) {
        this.distanceCovered = this.getDistanceCovered() + yards;
    }

    public String getName() {
        return name;
    }

    public Integer getLane() {
        return lane;
    }

    public Integer getDistanceCovered() {
        return distanceCovered;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Horse horse = (Horse) o;

        if (!lane.equals(horse.lane)) return false;
        if (!name.equals(horse.name)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + lane.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Horse{" +
            "position=" + position +
            ", lane=" + lane +
            ", name='" + name + '\'' +
            '}'+"\n";
    }
}
