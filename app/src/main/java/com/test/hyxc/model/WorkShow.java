package com.test.hyxc.model;

import java.io.Serializable;
import java.util.List;
import java.util.PrimitiveIterator;

public class WorkShow  implements Serializable {
   private Integer work_author;
   private String user_name;
   private List<WorkResource> resourcesList;
   private String user_nickname;
   private String user_headimg;
   private Integer work_id;
   private Integer work_island;
   private Integer user_island_privilege;
   private String work_island_name;
   private String work_island_logo;
   private String work_longitude;
   private String work_latitude;
   private String work_state;
   private String work_city;
   private Time work_time;
   private String work_district;
   private Integer work_show_count;
   private int work_like_count;
   private  int work_share_count;
   private String work_text;
   private String work_position;
   private Integer work_type;
   private String work_island_category_parent;
   private Integer work_island_category_parent_id;
   private Integer work_island_category_id;
   private String work_island_category;
   private Integer work_discuss_count;
   //
   private String work_show_image_h;
   private String work_show_image_w;
   private String work_title;
   private String work_show_image;
   private String subtype;
   //海岛信息
   private Integer is_open;
   private Integer is_join_allow;
   //点赞状态
   private Integer likeState;
   //是否已经关注该用户
   private Integer friendState;
   //默认显示宽度 高度
   private  int firstWidth=300;
   private int firstHeight=500;
   //评论回复 简略显示的信息
   private List<UserWorkDiscussShow> userWorkDiscussShowList;
   ///////用于显示的额外信息
   private int end=0;
   public Integer getWork_author() {
      return work_author;
   }

   public String getUser_name() {
      return user_name;
   }

   public List<WorkResource> getResourcesList() {
      return resourcesList;
   }

   public String getUser_nickname() {
      return user_nickname;
   }

   public String getUser_headimg() {
      return user_headimg;
   }

   public Integer getWork_id() {
      return work_id;
   }

   public Integer getWork_island() {
      return work_island;
   }

   public String getWork_island_name() {
      return work_island_name;
   }

   public String getWork_longitude() {
      return work_longitude;
   }

   public String getWork_latitude() {
      return work_latitude;
   }

   public String getWork_state() {
      return work_state;
   }

   public String getWork_city() {
      return work_city;
   }

   public String getWork_district() {
      return work_district;
   }

   public Integer getWork_show_count() {
      return work_show_count;
   }

   public int getWork_like_count() {
      return work_like_count;
   }

   public String getWork_text() {
      return work_text;
   }

   public String getWork_position() {
      return work_position;
   }

   public Integer getWork_type() {
      return work_type;
   }


   public String getWork_island_category_parent() {
      return work_island_category_parent;
   }

   public Integer getWork_island_category_parent_id() {
      return work_island_category_parent_id;
   }

   public Integer getWork_island_category_id() {
      return work_island_category_id;
   }

   public String getWork_island_category() {
      return work_island_category;
   }

   public Integer getWork_discuss_count() {
      return work_discuss_count;
   }

   public String getWork_show_image_h() {
      return work_show_image_h;
   }

   public String getWork_show_image_w() {
      return work_show_image_w;
   }

   public String getWork_title() {
      return work_title;
   }

   public String getWork_show_image() {
      return work_show_image;
   }

   public void setWork_author(Integer work_author) {
      this.work_author = work_author;
   }

   public void setUser_name(String user_name) {
      this.user_name = user_name;
   }

   public void setResourcesList(List<WorkResource> resourcesList) {
      this.resourcesList = resourcesList;
   }

   public void setUser_nickname(String user_nickname) {
      this.user_nickname = user_nickname;
   }

   public void setUser_headimg(String user_headimg) {
      this.user_headimg = user_headimg;
   }

   public void setWork_id(Integer work_id) {
      this.work_id = work_id;
   }

   public void setWork_island(Integer work_island) {
      this.work_island = work_island;
   }

   public void setWork_island_name(String work_island_name) {
      this.work_island_name = work_island_name;
   }

   public void setWork_longitude(String work_longitude) {
      this.work_longitude = work_longitude;
   }

   public void setWork_latitude(String work_latitude) {
      this.work_latitude = work_latitude;
   }

   public void setWork_state(String work_state) {
      this.work_state = work_state;
   }

   public void setWork_city(String work_city) {
      this.work_city = work_city;
   }

   public void setWork_district(String work_district) {
      this.work_district = work_district;
   }

   public void setWork_show_count(Integer work_show_count) {
      this.work_show_count = work_show_count;
   }

   public void setWork_like_count(int work_like_count) {
      this.work_like_count = work_like_count;
   }

   public void setWork_text(String work_text) {
      this.work_text = work_text;
   }

   public void setWork_position(String work_position) {
      this.work_position = work_position;
   }

   public void setWork_type(Integer work_type) {
      this.work_type = work_type;
   }

   public void setWork_island_category_parent(String work_island_category_parent) {
      this.work_island_category_parent = work_island_category_parent;
   }

   public void setWork_island_category_parent_id(Integer work_island_category_parent_id) {
      this.work_island_category_parent_id = work_island_category_parent_id;
   }

   public void setWork_island_category_id(Integer work_island_category_id) {
      this.work_island_category_id = work_island_category_id;
   }

   public void setWork_island_category(String work_island_category) {
      this.work_island_category = work_island_category;
   }

   public void setWork_discuss_count(Integer work_discuss_count) {
      this.work_discuss_count = work_discuss_count;
   }

   public void setWork_show_image_h(String work_show_image_h) {
      this.work_show_image_h = work_show_image_h;
   }

   public void setWork_show_image_w(String work_show_image_w) {
      this.work_show_image_w = work_show_image_w;
   }

   public void setWork_title(String work_title) {
      this.work_title = work_title;
   }

   public void setWork_show_image(String work_show_image) {
      this.work_show_image = work_show_image;
   }

   public Time getWork_time() {
      return work_time;
   }

   public void setWork_time(Time work_time) {
      this.work_time = work_time;
   }

   public String getSubtype() {
      return subtype;
   }

   public void setSubtype(String subtype) {
      this.subtype = subtype;
   }

   public String getWork_island_logo() {
      return work_island_logo;
   }

   public void setWork_island_logo(String work_island_logo) {
      this.work_island_logo=work_island_logo;
   }

    public Integer getLikeState() {
        return likeState;
    }

    public void setLikeState(int likeState) {
        this.likeState=likeState;
    }



   public void setLikeState(Integer likeState) {
      this.likeState=likeState;
   }

   public int getFirstWidth() {
      return firstWidth;
   }

   public int getFirstHeight() {
      return firstHeight;
   }

   public void setFirstWidth(int firstWidth) {
      this.firstWidth=firstWidth;
   }

   public void setFirstHeight(int firstHeight) {
      this.firstHeight=firstHeight;
   }

   public int getWork_share_count() {
      return work_share_count;
   }

   public void setWork_share_count(int work_share_count) {
      this.work_share_count=work_share_count;
   }

   public List<UserWorkDiscussShow> getUserWorkDiscussShowList() {
      return userWorkDiscussShowList;
   }

   public void setUserWorkDiscussShowList(List<UserWorkDiscussShow> userWorkDiscussShowList) {
      this.userWorkDiscussShowList=userWorkDiscussShowList;
   }

   public int getEnd() {
      return end;
   }

   public void setEnd(int end) {
      this.end=end;
   }

   public Integer getFriendState() {
      return friendState;
   }

   public void setFriendState(Integer friendState) {
      this.friendState = friendState;
   }

   public Integer getIs_open() {
      return is_open;
   }

   public Integer getIs_join_allow() {
      return is_join_allow;
   }

   public void setIs_open(Integer is_open) {
      this.is_open = is_open;
   }

   public void setIs_join_allow(Integer is_join_allow) {
      this.is_join_allow = is_join_allow;
   }

   public void setUser_island_privilege(Integer user_island_privilege) {
      this.user_island_privilege = user_island_privilege;
   }

   public Integer getUser_island_privilege() {
      return user_island_privilege;
   }
   /*public String toString(){
      return  "{" +
              "work_author=" + work_author +","+
              "user_name" + user_name +","+

              "}";
   }*/
}