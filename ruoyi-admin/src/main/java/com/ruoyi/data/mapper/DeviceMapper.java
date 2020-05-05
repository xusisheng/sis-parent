package com.ruoyi.data.mapper;

import java.util.List;

import com.ruoyi.data.domain.Ability;
import com.ruoyi.data.domain.Device;
import com.ruoyi.data.domain.DeviceAbilityVO;
import com.ruoyi.data.domain.StationDeviceVO;

/**
 * 设备Mapper接口
 * 
 * @author xusisheng
 * @date 2020-04-30
 */
public interface DeviceMapper 
{
    /**
     * 查询设备
     * 
     * @param id 设备ID
     * @return 设备
     */
    public Device selectDeviceById(Long id);

    /**
     * 查询设备列表
     * 
     * @param device 设备
     * @return 设备集合
     */
    public List<Device> selectDeviceList(Device device);

    /**
     * 新增设备
     * 
     * @param device 设备
     * @return 结果
     */
    public int insertDevice(Device device);

    /**
     * 修改设备
     * 
     * @param device 设备
     * @return 结果
     */
    public int updateDevice(Device device);

    /**
     * 删除设备
     * 
     * @param id 设备ID
     * @return 结果
     */
    public int deleteDeviceById(Long id);

    /**
     * 批量删除设备
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteDeviceByIds(String[] ids);

    /**
     * 根据条件分页查询已分配设备列表
     *
     * @param stationId 台站ID
     * @return 设备能力集合信息
     */
    public List<Device> selectAllocatedList(Long stationId);

    /**
     * 根据条件分页查询未分配设备列表
     *
     * @param stationDeviceVO 台站设备
     * @return 设备能力集合信息
     */
    public List<Device> selectUnallocatedList(StationDeviceVO stationDeviceVO);
}
