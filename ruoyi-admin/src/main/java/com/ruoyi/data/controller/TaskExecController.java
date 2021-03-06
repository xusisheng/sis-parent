package com.ruoyi.data.controller;

import java.util.List;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.data.domain.TaskExec;
import com.ruoyi.data.service.TaskExecService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 作战任务执行信息Controller
 * 
 * @author xusisheng
 * @date 2020-04-30
 */
@Controller
@RequestMapping("/data/task/exec")
public class TaskExecController extends BaseController
{
    private String prefix = "data/task/exec";

    @Autowired
    private TaskExecService taskExecService;

    @RequiresPermissions("data:task:view")
    @GetMapping()
    public String exec()
    {
        return prefix + "/exec";
    }

    /**
     * 查询作战任务执行信息列表
     */
    @RequiresPermissions("data:task:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(TaskExec taskExec)
    {
        startPage();
        List<TaskExec> list = taskExecService.selectTaskExecList(taskExec);
        return getDataTable(list);
    }

    /**
     * 导出作战任务执行信息列表
     */
    @RequiresPermissions("data:task:export")
    @Log(title = "作战任务执行信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(TaskExec taskExec)
    {
        List<TaskExec> list = taskExecService.selectTaskExecList(taskExec);
        ExcelUtil<TaskExec> util = new ExcelUtil<TaskExec>(TaskExec.class);
        return util.exportExcel(list, "exec");
    }

    /**
     * 新增作战任务执行信息
     */
    @GetMapping("/add/{taskId}")
    public String add(@PathVariable("taskId") Long taskId, ModelMap mmap)
    {
        mmap.put("taskId", taskId);
        return prefix + "/add";
    }

    /**
     * 新增保存作战任务执行信息
     */
    @RequiresPermissions("data:task:add")
    @Log(title = "作战任务执行信息", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(TaskExec taskExec)
    {
        return toAjax(taskExecService.insertTaskExec(taskExec));
    }

    /**
     * 修改作战任务执行信息
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        TaskExec taskExec = taskExecService.selectTaskExecById(id);
        mmap.put("taskExec", taskExec);
        return prefix + "/edit";
    }

    /**
     * 修改保存作战任务执行信息
     */
    @RequiresPermissions("data:task:edit")
    @Log(title = "作战任务执行信息", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(TaskExec taskExec)
    {
        return toAjax(taskExecService.updateTaskExec(taskExec));
    }

    /**
     * 删除作战任务执行信息
     */
    @RequiresPermissions("data:task:remove")
    @Log(title = "作战任务执行信息", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(taskExecService.deleteTaskExecByIds(ids));
    }
}
