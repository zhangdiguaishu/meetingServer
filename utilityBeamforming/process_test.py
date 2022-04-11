# 对每一个wav进行互相关，然后波束成形，得到的增强后的结果保存成一个wav文件
import argparse
import numpy as np
import math
import os
import soundfile as sf
from corr.xcorr import bf_corr
import beamforming.beamformer as bf

def process_data(data, rate, file_index):
    wav1 = data[0]      # 1 和 5做互相关，现在是12个mic
    wav2 = data[4]
    corr_result = bf_corr(wav1, wav2)  # 调用互相关模块
    print("general_corr_result:", corr_result)

    # 调用BF模块
    degs = corr_result[1]
    ratios = corr_result[2]
    new_ratio = []
    new_deg = []                       # 非nan角度
    for i, deg in enumerate(degs):
        if not math.isnan(deg):
            new_deg.append(deg)
            new_ratio.append(ratios[i])

    look_deg = []                       # 找出峰值最大的两个峰对应的角度(已知声源数为2)
    index = []
    Inf = -1
    for i in range(2):
        index.append(new_ratio.index(max(new_ratio)))
        new_ratio[new_ratio.index(max(new_ratio))] = Inf
    index.sort()
    # print("index-sort:", index)
    for i in index:
        look_deg.append(new_deg[i])
    # print("期望角度：", look_deg)

    bf_results = []                         # 维度：有效角度数x2
    for i, deg in enumerate(look_deg):
        bf_results.append([])

        multi_data = data[0: 4].T
        bf_result = bf.get_enhanced_speech(deg, multi_data)
        bf_results[i].append(bf_result)

        sf.write(processedAudioDir + "/bf_result_" + str(file_index) + str(i) + ".wav",
                 bf_result / np.max(np.abs(bf_result)) * 0.65,
                 rate)
    print("bf计算完成")

processedAudioDir = ""

def main():
    global processedAudioDir

    parser = argparse.ArgumentParser()
    parser.add_argument("meetingIndex")
    args = parser.parse_args()

    idx = args.meetingIndex

    cnt = 1
    originalAudioDir = "D:\\_code\\project_java_meetingServer\\ideaProj\\receivedRecords\\" + str(idx)
    processedAudioDir = "D:\\_code\\project_java_meetingServer\\ideaProj\\processedRecords\\" + str(idx);
    if not os.path.exists(processedAudioDir):
        os.makedirs(processedAudioDir)
    terminatingFileName = originalAudioDir + "\\done"

    while True:
        if os.path.exists(terminatingFileName):
            print("fully processed")
            return
        curFileName = originalAudioDir + "\\" + str(cnt) + ".wav"
        if os.path.exists(curFileName):
            wav, rate = sf.read(curFileName, dtype="int16")
            process_data(wav.T, rate, cnt - 1)
            cnt += 1

if __name__ == "__main__":
    main()




