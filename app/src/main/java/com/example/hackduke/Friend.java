package com.example.hackduke;

import androidx.annotation.Nullable;

import java.util.Map;

public class Friend {

    public Map<String,Object> FriendData;

    public Friend(){

    }

    public Friend(Map<String,Object> map){
        FriendData = map;
    }

    public void updateFriendData(Map<String,Object> map){
        FriendData = map;
    }

    public Map<String,Object> getFriendData(){
        return FriendData;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        Friend temp = (Friend)obj;
        if(temp.getFriendData().containsKey("Uid")&&this.getFriendData().containsKey("Uid")){
            if(temp.getFriendData().get("Uid").equals(this.getFriendData().get("Uid"))==true){
                return true;
            }
            return false;
        }
       return false;
    }
}


