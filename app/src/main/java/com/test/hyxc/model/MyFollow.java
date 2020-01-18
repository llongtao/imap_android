package com.test.hyxc.model;

import java.io.Serializable;

/**
 * Created by Mac on 2019/4/18.
 */

public class MyFollow implements Serializable{
      private Integer followType;
      private Island island;
      private FriendDetail  friendDetail;

    public Integer getFollowType() {
        return followType;
    }


    public void setFollowType(Integer followType) {
        this.followType = followType;
    }

    public Island getIsland() {
        return island;
    }

    public void setIsland(Island island) {
        this.island = island;
    }

    public FriendDetail getFriendDetail() {
        return friendDetail;
    }

    public void setFriendDetail(FriendDetail friendDetail) {
        this.friendDetail = friendDetail;
    }
}
