package com.example.nouran.playground.Models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;


/**
 * Created by Nouran on 4/14/2018.
 */

public class Places implements Serializable {
    @SerializedName("routes")
    List<Route> routes;

    public List<Route> getRoutes() {
        return routes;
    }

    public void setRoutes(List<Route> routes) {
        this.routes = routes;
    }

    public class Route {
        @SerializedName("legs")
        List<Legs> legs;

        public List<Legs> getLegs() {
            return legs;
        }

        public void setLegs(List<Legs> legs) {
            this.legs = legs;
        }
    }

    public class Legs {
        @SerializedName("distance")
        Distance distance;
        @SerializedName("duration")
        Duration duration;
        @SerializedName("steps")
        List<Steps> steps;

        public Distance getDistance() {
            return distance;
        }

        public void setDistance(Distance distance) {
            this.distance = distance;
        }

        public Duration getDuration() {
            return duration;
        }

        public void setDuration(Duration duration) {
            this.duration = duration;
        }

        public List<Steps> getSteps() {
            return steps;
        }

        public void setSteps(List<Steps> steps) {
            this.steps = steps;
        }
    }

    public class Distance {
        @SerializedName("text")
        String text;

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }
    }

    public class Duration {
        @SerializedName("text")
        String text;

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

    }

    public class Steps {
        @SerializedName("polyline")
        Polyline polyline;

        public Polyline getPolyline() {
            return polyline;
        }

        public void setPolyline(Polyline polyline) {
            this.polyline = polyline;
        }

    }

    public class Polyline {
        @SerializedName("points")
        String points;

        public String getPoints() {
            return points;
        }

        public void setPoints(String points) {
            this.points = points;
        }
    }


}
