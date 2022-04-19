import numpy as np
import math
from collections import Counter
import soundfile as sf
from corr.xcorr import bf_corr
import beamforming.beamformer as bf

main_path = "./0405-test-data/a/"
audioList_path = "./0405-test-data/a/audioList.txt"
enhanced_wav = "./0405-test-data/enhanced/"


def corr_data(filenames):
    corr_results = []
    for i, file in enumerate(filenames):
        path = main_path + file
        wav, rate = sf.read(path, dtype="float32")
        wav1 = wav.T[0]                                     # 1 和 5做互相关，现在是12个mic
        wav2 = wav.T[4]
        corr_result = bf_corr(wav1, wav2)                   # 调用互相关模块
        # print("general_corr_result:", corr_result)
        corr_results.append(corr_result[1])

    # 投票
    corr_results = [n for a in corr_results for n in a]
    new_corr_results = [d for d in corr_results if not math.isnan(d)]   # 非nan角度
    corr_count = Counter(new_corr_results)               # 返回出现频率最高的两个数认为是声源
    print(corr_count)
    corr_count = corr_count.most_common(2)
    corr_final = [item[0] for item in corr_count]
    return corr_final


# 对1s的数据进行处理，得到包含两个角度的bf结果
def process_data(data, look_deg):
    bf_results = []  # 维度：有效角度数x2
    for i, deg in enumerate(look_deg):
        multi_data = data[0: 4].T
        bf_result = bf.get_enhanced_speech(deg, multi_data)
        bf_result = bf_result.tolist()
        bf_results.append(bf_result)

    print("bf计算完成")
    return bf_results


filenames = []
with open(audioList_path, "r") as f:
    for line in f.readlines():
        line = line.strip('\n')         # 去掉列表中每个元素的换行符
        filenames.append(line)

deg = corr_data(filenames)              # 求互相关角度
print(deg)

merge_bf_results = []
for i, filename in enumerate(filenames):
    full_path = main_path + filename
    wav, rate = sf.read(full_path, dtype="float32")
    # print(wav.shape)
    bf_results = process_data(wav.T, deg)
    merge_bf_results.append(bf_results)


num = len(merge_bf_results[0])              # s声源个数

for i in range(num):
    a = [x[i] for x in merge_bf_results]
    enhanced = np.array(a)
    enhanced = enhanced.flatten()
    print("enhanced.shape", enhanced.shape)
    sf.write(enhanced_wav + "bf_result" + "_" + str(i) + ".wav",
             enhanced / np.max(np.abs(enhanced)) * 0.65,
             48000)
