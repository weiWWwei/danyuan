package com.ww.backend.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ww.backend.sys.entity.Student;
import com.ww.backend.sys.mapper.StudentMapper;
import com.ww.backend.sys.mapper.UserRoleMapper;
import com.ww.backend.sys.service.IStudentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ww
 * @since 2023-05-03
 */
@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements IStudentService {
    @Resource
    private StudentMapper studentMapper;

    @Override
    public Student getStudentByNumber(String number) {
        LambdaQueryWrapper<Student> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Student::getNumber,number);
        Student student = studentMapper.selectOne(wrapper);
        return student;
    }
}
