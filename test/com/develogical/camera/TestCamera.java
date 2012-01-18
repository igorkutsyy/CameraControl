package com.develogical.camera;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.lang.reflect.Member;

@RunWith(value = JMock.class)
public class TestCamera {

    
    Mockery context = new Mockery();
    
    Sensor sensor = context.mock(Sensor.class);
    MemoryCard memoryCard = context.mock(MemoryCard.class);
    WriteListener writeListener = context.mock(WriteListener.class);
    Camera camera = new Camera(sensor,memoryCard);

    
    @Test
    public void switchingTheCameraOnPowersUpTheSensor() {
        
        context.checking(new Expectations() {{
            exactly(1).of(sensor).powerUp();
        }}
        );
        
        camera.powerOn();        
        
        
        
         // write you test here
    }
    
    @Test
    public void switchingTheCameraOffPowersDownTheSensor(){
        
        context.checking(new Expectations() {{
            exactly(1).of(sensor).powerDown();
            //exactly(1).of(writeListener).writeComplete();
        }});


        camera.powerOff();
        
        
    }
    
    @Test
    public void pressTheCameraShutterWhenPowerIsOff(){

        //assumed power is off at the moment;
        context.checking(new Expectations(){{
            never(sensor);
        }});

        camera.pressShutter();
    }

    @Test
    public void pressTheCameraShutterWhenCameraIsOn()
    {
        //pressing the shutter with the power on copies data from the sensor to the memory card


        final byte[] data = {0,1};//some data
        Camera camera = getCameraPoweredOn();



        
        context.checking(new Expectations(){
            {
                exactly(1).of(sensor).readData(); will(returnValue(data));
                exactly(1).of(memoryCard).write(data);
        }
        });

        camera.pressShutter();
        
    }

    private Camera getCameraPoweredOn() {
        Camera camera = new Camera(sensor, memoryCard);

        context.checking(new Expectations() {
            {
                exactly(1).of(sensor).powerUp();
            }
        });

        camera.powerOn();
        return camera;
    }


    
    @Test
    public void switchingTheCameraOffWhenDataIsWrittenDoesNotPowerDownTheSensor(){
        //if data is currently being writte, swithcing the camera off does not power down the sensor    

        Camera camera = getCameraPoweredOn();

        getCameraPressShutter(camera);

        context.checking(new Expectations(){{
            never(sensor).powerDown();
        
        }
        });
        
        
        camera.powerOff();

    }

    private void getCameraPressShutter(Camera camera) {


        final byte[] data = {0,1};//some data

        context.checking(new Expectations(){
            {
                exactly(1).of(sensor).readData(); will(returnValue(data));
                exactly(1).of(memoryCard).write(data);
            }
        });

        camera.pressShutter();
    }


    @Test
    public void switchingTheCameraOffWhenWritingComplete(){

        Camera camera = getCameraPoweredOn();

        getCameraPressShutter(camera);




    }


}
