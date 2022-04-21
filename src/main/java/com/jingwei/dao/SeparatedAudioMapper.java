package com.jingwei.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface SeparatedAudioMapper {
    public void intializeNewMeeting(int meetingIndex);
    public List<String> queryAudioNameByMeetingIndex(int meetingIndex);
}
