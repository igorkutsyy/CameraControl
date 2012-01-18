package com.develogical.camera;



public class Camera implements WriteListener{
    
    private Sensor sensor;
    private MemoryCard memoryCard;

    public enum cameraStatus {CAMERA_ON,CAMERA_OFF};
    public enum MemoryWriteStatus {WRITE_NOT_COMPLETE,WRITE_COMPLETE}
    private cameraStatus currentStatus;
    private MemoryWriteStatus isWriteComplete;


    public Camera(Sensor sensor,MemoryCard memoryCard){
        
        this.sensor = sensor;
        this.memoryCard = memoryCard;
        currentStatus = cameraStatus.CAMERA_OFF;
        isWriteComplete = MemoryWriteStatus.WRITE_COMPLETE;


        
        
    }
    
    public void writeComplete(){

        isWriteComplete = MemoryWriteStatus.WRITE_COMPLETE;
        
        
    }

    public void pressShutter() {
        // not implemented

        if (currentStatus == cameraStatus.CAMERA_OFF) {

        }

        if(currentStatus == cameraStatus.CAMERA_ON){


            byte[] data = sensor.readData();

            if((data!=null) && (data.length>=1)){
                memoryCard.write(data);
                isWriteComplete = MemoryWriteStatus.WRITE_NOT_COMPLETE;
            }

        }



    }

    public void powerOn() {


        sensor.powerUp();
        currentStatus = cameraStatus.CAMERA_ON;

    }

    public void powerOff() {

        if(isWriteComplete==MemoryWriteStatus.WRITE_COMPLETE){
            sensor.powerDown();
        }
        currentStatus = cameraStatus.CAMERA_OFF;


    }

}

