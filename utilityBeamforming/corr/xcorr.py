import numpy as np
import os
import wave
import matplotlib.pyplot as plt


# x = np.array([1,2,3])
# y = np.array([0,1,0.5])
# corr = np.correlate(x,y,"full")
# discrete_shift = corr.argmax() - (np.size(y) - 1)
# print(discrete_shift)

def peak_search(alist, begin, end):
    # 找峰函数，可以优化
    ans = []
    for i in range(begin + 1, end):
        if alist[i] > alist[i - 1] and alist[i] > alist[i + 1]:
            ans.append(i)
    return ans


def bf_corr(wavedata1, wavedata2, samplingrate=48000):
    # 初始BF函数，用来初步确定峰的位置、计算角度并且计算峰的相对强度
    corr = np.correlate(wavedata1, wavedata2, "full")
    # print("wavedata1.size:", np.size(wavedata1))
    # plt.plot(corr)
    # plt.show()
    peak = peak_search(corr, np.size(wavedata1) - 15, np.size(wavedata1) + 15)
    discrete_shift_all = []
    deg = []
    ratio = []
    averageAmp = 0
    for i in range(np.size(wavedata1) - 15, np.size(wavedata1) + 16):
        averageAmp = averageAmp + abs(corr[i])
    averageAmp = averageAmp / 31
    for i in range(len(peak)):
        discrete_shift = peak[i] - (np.size(wavedata1) - 1)
        discrete_shift_all.append(discrete_shift)
        # 增加sin值的判断, 但是会不会对精度有损失？
        # sin_value = (discrete_shift * 340) / (samplingrate * 0.072)
        # if sin_value > 1.0:
        #     sin_value = 1.0
        # if sin_value < -1.0:
        #     sin_value = -1.0
        # deg.append(np.arcsin(sin_value) * 180 / np.pi)

        deg.append(np.arcsin((discrete_shift * 340) / (samplingrate * 0.072)) * 180 / np.pi)
        ratio.append(corr[peak[i]] / averageAmp)
    return (discrete_shift_all, deg, ratio)


def bf_corr_res(wavedata1, wavedata2, peak, ratio):
    # 计算峰的相对强度变化，输出新的峰的强度并且给出布尔值判断
    corr = np.correlate(wavedata1, wavedata2, "full")
    averageAmp = 0
    for i in range(np.size(wavedata1) - 15, np.size(wavedata1) + 16):
        averageAmp = averageAmp + abs(corr[i])
    averageAmp = averageAmp / 31
    ratio_r = corr[peak] / averageAmp
    if ratio_r > ratio:
        return (ratio_r, True)
    else:
        return (ratio_r, False)

# file1 = 'wavs/音轨.wav'
# file2 = 'wavs/音轨-5.wav'
# f1 = wave.open(file1, 'rb')
# f2 = wave.open(file2, 'rb')
# params = f1.getparams()
# nchannels1, sampwidth1, framerate1, nframes1 = params[:4]
# print(nchannels1, sampwidth1, framerate1, nframes1)
# params = f2.getparams()
# nchannels2, sampwidth2, framerate2, nframes2 = params[:4]
# print(nchannels2, sampwidth2, framerate2, nframes2)
# strData1 = f1.readframes(44100)
# strData2 = f2.readframes(44100)
# waveData1 = np.frombuffer(strData1,dtype = np.short)
# waveData1 = waveData1 * 1.0 / (max(abs(waveData1)))
# waveData2 = np.frombuffer(strData2,dtype = np.short)
# waveData2 = waveData2 * 1.0 / (max(abs(waveData2)))
# corr = np.correlate(waveData1,waveData2,"full")
# discrete_shift = corr.argmax() - (44100 - 1)
# print(discrete_shift,corr.argmax(),waveData1)
# print(bf_corr(waveData1, waveData2, 44100))
# plt.plot(corr)
# plt.show()


# time = np.arange(0,nframes1) * (1.0 / framerate1)
# plt.plot(time,waveData1)
# plt.xlabel("Time(s)")
# plt.ylabel("Amplitude")
# plt.title("Single channel wavedata")
# plt.grid('on')
# plt.show()

# x = np.array([0,1,3,4,5,6,5,4,3,2,1,4,5,6,10,9])
# print(peak_search(x,0,len(x)-1))
