<template>
  <div>
    <el-card id="search">
      <el-row>
        <el-col :span="20">
          <el-input v-model="searchModel.number" placeholder="党员证编号" clearable></el-input>
          <el-input v-model="searchModel.uid" placeholder="身份证号" clearable></el-input>
          <el-button @click="getStudentList" type="primary" round icon="el-icon-search">查询</el-button>
          <el-button class="filter-item" type="primary" icon="el-icon-download" @click="onexcel">
            导出
          </el-button>
          <!-- <el-button
            @click="filterStudentList"
            type="primary"
            round
            icon="el-icon-refresh-right"
            >筛选</el-button
          > -->
        </el-col>
        <el-col :span="4" align="right">
          <el-button @click="openEditUI(null)" type="primary" icon="el-icon-plus" circle></el-button>
        </el-col>
      </el-row>
    </el-card>
    <el-card>
      <el-table :data="studentList" style="width: 100%">
        <el-table-column label="#" width="60">
          <template slot-scope="scope">
            {{
              (searchModel.pageNo - 1) * searchModel.pageSize + scope.$index + 1
            }}
          </template>
        </el-table-column>
        <el-table-column label="党员证编号" prop="number" width="200">
        </el-table-column>
        <el-table-column label="身份证" prop="uid" width="200">
        </el-table-column>
        <el-table-column label="姓名" prop="name"> </el-table-column>
        <el-table-column label="民族" prop="chinese"> </el-table-column>
        <el-table-column label="性别" prop="score"> </el-table-column>
        <el-table-column label="人员类别" prop="math"> </el-table-column>
        <el-table-column label="学历" prop="english"> </el-table-column>
        <el-table-column label="加入党组织日期" prop="physics"> </el-table-column>
        <el-table-column label="转为正式党员日期" prop="biology"> </el-table-column>
        <el-table-column label="家庭住址" prop="chemistry"> </el-table-column>
        <el-table-column label="操作" align="right">
          <template slot-scope="scope">
            <el-button @click="openEditUI(scope.row.id)" type="primary" icon="el-icon-edit" size="mini"
              circle></el-button>
            <el-button @click="deleteStudent(scope.row)" type="danger" icon="el-icon-delete" size="mini"
              circle></el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
    <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
      :current-page="searchModel.pageNo" :page-sizes="[5, 10, 20, 30]" :page-size="searchModel.pageSize"
      layout="total, sizes, prev, pager, next, jumper" :total="total">
    </el-pagination>
    <el-dialog @close="clearForm" :title="title" :visible.sync="dialogFormVisible">
      <el-form :model="studentForm" ref="studentFormRef" :rules="rules">
        <el-form-item label="名字" prop="name" :label-width="formLabelWidth">
          <el-input v-model="studentForm.name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="党员证编号" prop="number" :label-width="formLabelWidth">
          <el-input v-model="studentForm.number" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="身份证" prop="uid" :label-width="formLabelWidth">
          <el-input v-model="studentForm.uid" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="民族" :label-width="formLabelWidth">
          <el-input v-model="studentForm.chinese" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="性别" :label-width="formLabelWidth">
          <el-input v-model="studentForm.score" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="人员类别" :label-width="formLabelWidth">
          <el-input v-model="studentForm.math" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="学历" :label-width="formLabelWidth">
          <el-input v-model="studentForm.english" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="加入党组织日期" :label-width="formLabelWidth">
          <el-date-picker v-model="studentForm.physics" type="date" placeholder="选择日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="转为正式党员日期" :label-width="formLabelWidth">
          <el-date-picker v-model="studentForm.biology" type="date" placeholder="选择日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="家庭住址" :label-width="formLabelWidth">
          <el-input v-model="studentForm.chemistry" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="saveStudent">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import studentApi from "@/api/student";
export default {
  data() {
    var checkNumber = (rule, value, callback) => {
      var reg = /\d{11}$/;
      if (!reg.test(value)) {
        return callback(new Error("党员证编号格式错误"));
      }
      callback();
    };
    var checkUid = (rule, value, callback) => {
      var reg =
        /^[1-9]\d{5}(18|19|20)\d{2}((0[1-9])|(1[0-2]))(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]$/;
      if (!reg.test(value)) {
        return callback(new Error("身份证格式错误"));
      }
      callback();
    };
    return {
      downloadLoading: false,
      formLabelWidth: "130px",
      studentForm: {},
      dialogFormVisible: false,
      title: "",
      total: 0,
      searchModel: {
        pageNo: 1,
        pageSize: 10,
      },
      studentList: [],
      rules: {
        name: [
          { required: true, message: "请输入名字", trigger: "blur" },
          {
            min: 2,
            max: 10,
            message: "长度在 2 到 10 个字符",
            trigger: "blur",
          },
        ],
        number: [
          { required: true, message: "请输入党员证编号", trigger: "blur" },
          { validator: checkNumber, trigger: "blur" },
        ],
        uid: [
          { required: true, message: "请输入身份证号", trigger: "blur" },
          { validator: checkUid, trigger: "blur" },
        ],
      },
    };
  },
  methods: {
    onexcel() {
      const { export_json_to_excel } = require("@/excle/Export2Excel"); // 将生成excel的Export2Excel.js引入

      let title = [
        "党员证编号",
        "身份证号",
        "姓名",
        "民族",
        "性别",
        "人员类别",
        "学历",
        "加入党组织日期",
        "家庭住址",
        "转为正式党员日期"
      ]; // 这里是excel表格的表头
      let key = [
        "number",
        "uid",
        "name",
        "chinese",
        "score",
        "math",
        "english",
        "physics",
        "biology",
        "chemistry"
      ]; // 设置生成列的数据属性
      function formatJson(filterVal, jsonData) {
        return jsonData.map((v) => filterVal.map((j) => v[j]));
      }

      export_json_to_excel(
        title,
        formatJson(key, this.studentList),
        "党员信息"
      ); // 调用生成方法
    },
    deleteStudent(student) {
      this.$confirm(`您确认删除 ${student.name} ? 信息?`, "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          studentApi.deleteStudentById(student.id).then((response) => {
            this.$message({
              type: "success",
              message: response.message,
            });
            this.getStudentList();
          });
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消删除",
          });
        });
    },
    saveStudent() {
      this.$refs.studentFormRef.validate((valid) => {
        if (valid) {
          studentApi.saveStudent(this.studentForm).then((response) => {
            if(response.code == 20001){
              this.$message({
              message: response.message,
              type:"error",
            });
            }else{
              this.$message({
              message: response.message,
              type: "success",
            });
            }
            
            // 关闭对话框
            this.dialogFormVisible = false;
            // 刷新表格
            this.getStudentList();
          });
        } else {
          console.log("error submit!!");
          return false;
        }
      });
    },
    clearForm() {
      this.studentForm = {};
      this.$refs.studentFormRef.clearValidate();
    },
    openEditUI(id) {
      if (id == null) {
        this.title = "新增党员";
      } else {
        this.title = "修改党员信息";
        studentApi.getStudentById(id).then((response) => {
          this.studentForm = response.data;
        });
      }

      this.dialogFormVisible = true;
    },
    handleEdit(index, row) {
      console.log(index, row);
    },
    handleDelete(index, row) {
      console.log(index, row);
    },
    handleSizeChange(pageSize) {
      this.searchModel.pageSize = pageSize;
      this.getStudentList();
    },
    handleCurrentChange(pageNo) {
      this.searchModel.pageNo = pageNo;
      this.getStudentList();
    },
    filterStudentList() {
      studentApi.filterStudentList(this.searchModel).then((response) => {
        this.studentList = response.data.rows;
        this.total = response.data.total;
      });
    },
    getStudentList() {
      studentApi.getStudentList(this.searchModel).then((response) => {
        this.studentList = response.data.rows;
        this.total = response.data.total;
      });
    },
  },
  created() {
    this.getStudentList();
  },
};
</script>

<style>
#search .el-input {
  width: 200px;
  margin-right: 10px;
}

.el-dialog .el-input {
  width: 80%;
}
</style>