package tools.MediaTool;

import java.util.List;

public class UseTest {
    /**
     * 视频剪切
     * @param startTime 视频剪切的开始时间
     * @param endTime 视频剪切的结束时间
     * @param FilePath 被剪切视频的路径
     * @param WorkingPath 剪切成功保存的视频路径
     * @param fileName 剪切成功保存的文件名
     */
    private synchronized void cutMp4(final long startTime, final long endTime, final String FilePath, final String WorkingPath, final String fileName){
        new Thread(new Runnable() {
            @Override
            public void run() {

                try{
                    //视频剪切
                    VideoClip videoClip= new VideoClip();//实例化VideoClip类
                    videoClip.setFilePath(FilePath);//设置被编辑视频的文件路径  FileUtil.getMediaDir()+"/test/laoma3.mp4"
                    videoClip.setWorkingPath(WorkingPath);//设置被编辑的视频输出路径  FileUtil.getMediaDir()
                    videoClip.setStartTime(startTime);//设置剪辑开始的时间
                    videoClip.setEndTime(endTime);//设置剪辑结束的时间
                    videoClip.setOutName(fileName);//设置输出的文件名称
                    videoClip.clip();//调用剪辑并保存视频文件方法（建议作为点击保存时的操作并加入等待对话框）
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * 录音功能
     */
    public static  void MediaRecordUse(){
        new MediaRecordUtil().recorderStart();
    }

    /**
     * 音视频合成
     */
    public static  void MuxAacMp4(String aacPath,String mp4Path,String outPath){
        boolean flag=Mp4ParseUtil.muxAacMp4(aacPath,mp4Path,outPath);
    }
    /**
     * 视频拼接
     */
    public static  void AppendMp4List(List<String> mp4PathList, String outPutPath){
        Mp4ParseUtil.appendMp4List(mp4PathList,outPutPath);
    }

}
