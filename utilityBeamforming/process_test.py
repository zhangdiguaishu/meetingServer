import argparse
import os.path

import numpy as np
import math
from collections import Counter
import soundfile as sf
from corr.xcorr import bf_corr
import beamforming.beamformer as bf

import time
processedAudioDir = ""

def corr_data(all_data):
    corr_results = []
    for wav in all_data:
        wav1 = wav.T[0]  # 1 和 5做互相关，现在是12个mic
        wav2 = wav.T[4]
        corr_result = bf_corr(wav1, wav2)
        corr_results.append(corr_result[1])
    # 投票
    corr_results = [n for a in corr_results for n in a]
    new_corr_results = [d for d in corr_results if not math.isnan(d)]  # 非nan角度
    corr_count = Counter(new_corr_results).most_common(2)  # 返回出现频率最高的两个数认为是声源
    corr_final = [item[0] for item in corr_count]
    return corr_final


# 对1s的数据进行处理，得到包含两个角度的bf结果
def bf_data(data, look_deg):
    bf_results = []  # 维度：有效角度数x2
    for i, deg in enumerate(look_deg):
        multi_data = data[0: 4].T
        bf_result = bf.get_enhanced_speech(deg, multi_data)
        bf_result = bf_result.tolist()
        bf_results.append(bf_result)

    return bf_results


def process_data(all_data, deg, cnt, processedAudioDir):
    merge_bf_results = []
    for wav in all_data:
        bf_results = bf_data(wav.T, deg)
        merge_bf_results.append(bf_results)
    # 合并
    num = len(merge_bf_results[0])  # s声源个数
    for i in range(num):
        a = [x[i] for x in merge_bf_results]
        enhanced = np.array(a)
        enhanced = enhanced.flatten()
        sf.write(processedAudioDir + "\\" + str(i+1) + ".wav",
                 enhanced / np.max(np.abs(enhanced)) * 0.65,
                 48000)


def main():
    global processedAudioDir
    parser = argparse.ArgumentParser()
    parser.add_argument("meetingIndex")
    args = parser.parse_args()

    idx = args.meetingIndex

    cnt = 1
    originalAudioDir = "D:\\_code\\project_java_meetingServer\\ideaProj\\receivedRecords\\" + str(idx)
    processedAudioDir = "D:\\_code\\project_java_meetingServer\\ideaProj\\processedRecords\\" + str(idx)
    #processedAudioDir = "D:\\_code\\project_java_meetingServer\\ideaProj\\src\\main\\resources\\static\\audios\\" + str(idx)
    terminatingFilePath = originalAudioDir + "\\done"
    
    if not os.path.exists(processedAudioDir):
        os.makedirs(processedAudioDir)
        
    all_data = []
    while True:
        curFileName = originalAudioDir + "\\" + str(cnt) + ".wav"
        if os.path.exists(curFileName):
            # 说明有新的音频段到来，需要进行处理
            wav, rate = sf.read(curFileName, dtype="int16")
            all_data.append(wav)
            cnt += 1
            continue
        elif os.path.exists(terminatingFilePath):
            break
        else:
            time.sleep(1)
            

    # 没有分离的总音频, 合并
    length = all_data[0].shape[0]
    channel = all_data[0].shape[1]
    ori = np.array(all_data)
    ori = ori.reshape((len(all_data) * length, channel))
    sf.write(processedAudioDir + "\\" + "0.wav", ori, 48000)

    deg = corr_data(all_data)
    process_data(all_data, deg, cnt, processedAudioDir)

    resultFilePath = os.path.join(processedAudioDir, "complete")
    resultFile = open(resultFilePath, 'x')
    resultFile.close()

if __name__ == "__main__":
    main()
