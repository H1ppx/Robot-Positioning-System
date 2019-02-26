package utils;

/*
 * (C) Copyright 2019 Will Chu and others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

public class Checkpoint {

    private final String name;
    private final Vector2d position;
    private final double heading;
    private final double threshold;

    public Checkpoint(String name, Vector2d position, double heading, double threshold){
        this.name = name;
        this.position = position;
        this.heading = heading;
        this.threshold = threshold;
    }

    public String getName() {
        return name;
    }

    public Vector2d getPosition() {
        return position;
    }

    public double getHeading(){
        return heading;
    }

    public boolean atPosition(Vector2d currentPosition){
        if(Math.abs(currentPosition.getX()-position.getX()) < threshold
                && Math.abs(currentPosition.getY()-position.getY()) < threshold) {
            return true;
        }
        return false;
    }
}