<!--普通用户进入时显示信息-->
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
        <el-table-column label="党员证编号" prop="number" width="150">
        </el-table-column>
        <el-table-column label="身份证" prop="uid" width="200">
        </el-table-column>
        <el-table-column label="姓名" prop="name" width="70"> </el-table-column>
        <el-table-column label="民族" prop="chinese" width="50"> </el-table-column>
        <el-table-column label="性别" prop="score" width="50"> </el-table-column>
        <el-table-column label="人员类别" prop="math" width="100"> </el-table-column>
        <el-table-column label="学历" prop="english" width="90"> </el-table-column>
        <el-table-column label="加入党组织日期" prop="physics" width="150"> </el-table-column>
        <el-table-column label="转为正式党员日期" prop="biology" width="150"> </el-table-column>
        <el-table-column label="家庭住址" prop="chemistry"> </el-table-column>
      </el-table>
    </el-card>
  </div>
</template>

<script>
import studentApi from "@/api/student";
export default {
  data() {
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
    saveStudent() {
      this.$refs.studentFormRef.validate((valid) => {
        if (valid) {
          studentApi.saveStudent(this.studentForm).then((response) => {
            this.$message({
              message: response.message,
              type: "success",
            });
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