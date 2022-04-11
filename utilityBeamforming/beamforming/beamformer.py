# -*- coding: utf-8 -*-
"""
Created on Tue Jan 15 17:49:51 2019

@author: a-kojima
"""
import numpy as np
import math
import time
import soundfile as sf
from . import my_minimum_variance_distortioless_response as mvdr
from . import util

SAMPLING_FREQUENCY = 48000
FFT_LENGTH = 2048  # 不知道这两个参数有没有影响
FFT_SHIFT = 1024

# MIC_ANGLE_VECTOR = np.array([0, 60, 120, 180, 270, 330])        # 这个参数到底指什么呢？麦克风角度向量，那如果是线性怎么表示

MIC_DIAMETER = 0.033
sound_speed = 343
MIC_ANGLE_VECTOR = np.array([0, 0, 180, 180])
# MIC_ANGLE_VECTOR = np.tile(MIC_ANGLE_VECTOR, 4)
# MIC_ANGLE_VECTOR = MIC_ANGLE_VECTOR.reshape(4, 4)
# print(MIC_ANGLE_VECTOR.shape)
# 以阵列中心为steering center
# MIC_ANGLE_VECTOR = np.array([45, 72, 108, 135,
#                              18, 45, 135, 162,
#                              342, 315, 225, 198,
#                              315, 288, 252, 225])
# distance = np.array([3 / math.sqrt(2), math.sqrt(10) / 2, -math.sqrt(10) / 2, -3 / math.sqrt(2),
#                      math.sqrt(10) / 2, math.sqrt(2) / 2, -math.sqrt(2) / 2, -math.sqrt(10) / 2,
#                      math.sqrt(10) / 2, math.sqrt(2) / 2, -math.sqrt(2) / 2, -math.sqrt(10) / 2,
#                      3 / math.sqrt(2), math.sqrt(10) / 2, -math.sqrt(10) / 2, -3 / math.sqrt(2)])

# 以第一个麦克风为steering center (第一行为1 2 3 4)
# MIC_ANGLE_VECTOR = np.array([0, 180, 180, 180,
#                              270, 225, 207, 198,
#                              270, 243, 225, 214,
#                              270, 252, 236, 225])
# distance = np.array([0, -1, -2, -3,
#                      -1, -math.sqrt(2), -math.sqrt(5), -math.sqrt(10),
#                      -2, -math.sqrt(5), -2 * math.sqrt(2), -math.sqrt(13),
#                      -3, -math.sqrt(10), -math.sqrt(13), -3 * math.sqrt(2)])

# 以第一个麦克风为steering center (第一行为1 5 9 13)
# x = MIC_ANGLE_VECTOR.reshape(4, 4).T
# MIC_ANGLE_VECTOR = x.flatten()
#
# y = distance.reshape(4, 4).T
# distance = y.flatten()


def get_enhanced_speech(look_direction, multi_channels_data):
    # start_time = time.time()
    # multi_channels_data = multi_channel_read()
    # print(multi_channels_data)
    complex_spectrum, _ = util.get_3dim_spectrum_from_data(multi_channels_data, FFT_LENGTH, FFT_SHIFT, FFT_LENGTH)
    # print("complex_spectrum:", complex_spectrum.shape)

    # if flag == 1:       # 行方向BF

    # else:               # 列方向BF
    #     MIC_ANGLE_VECTOR = np.array([90, 90, 270, 270])
    mvdr_beamformer = mvdr.my_minimum_variance_distortioless_response(MIC_ANGLE_VECTOR, MIC_DIAMETER,
                                                                      sampling_frequency=SAMPLING_FREQUENCY,
                                                                      fft_length=FFT_LENGTH, fft_shift=FFT_SHIFT,
                                                                      sound_speed=sound_speed)

    # steering_vector = mvdr_beamformer.get_steering_vector(look_direction)
    # print("steering vector:", steering_vector.shape)

    steering_vector = mvdr_beamformer.get_steering_vector(look_direction)
    # print("steering vector:", steering_vector.shape)
    # print("steering_vector:", steering_vector)

    # print("multi_channels_data:", multi_channels_data.shape)
    spatial_correlation_matrix = mvdr_beamformer.get_spatial_correlation_matrix(multi_channels_data)
    # print("spatial_correlation_matrix:", spatial_correlation_matrix.shape)

    beamformer = mvdr_beamformer.get_mvdr_beamformer(steering_vector,
                                                     spatial_correlation_matrix)  # 这一步得到权重w,所以只要得到权重并且乘以频谱就可以得到结果
    # print("beamformer:", beamformer.shape)

    enhanced_speech = mvdr_beamformer.apply_beamformer(beamformer, complex_spectrum)

    # total_time = time.time() - start_time
    # print("MVDR total time:", total_time)

    # sf.write(ENHANCED_WAV_NAME, enhanced_speech / np.max(np.abs(enhanced_speech)) * 0.65, SAMPLING_FREQUENCY)
    return enhanced_speech
