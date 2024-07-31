import { Component } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {AdminService} from "../../../admin/services/admin.service";
import {ActivatedRoute, Router} from "@angular/router";
import {MatSnackBar} from "@angular/material/snack-bar";
import {EmployeeService} from "../../services/employee.service";

@Component({
  selector: 'app-view-task-details',
  templateUrl: './view-task-details.component.html',
  styleUrls: ['./view-task-details.component.scss']
})
export class ViewTaskDetailsComponent {
  taskId: number = this.activatedRoute.snapshot.params["id"];
  taskData: any;
  comments: any;
  commentForm!: FormGroup;
  constructor(private fb: FormBuilder,
              private employeeService: EmployeeService,
              private router: Router,
              private snackbar: MatSnackBar,
              private activatedRoute: ActivatedRoute) {
  }
  ngOnInit() {
    this.getTaskById();
    this.getCommentsByTaskId();
    this.commentForm = this.fb.group({
      content: [null, Validators.required]
    })
  }

  getTaskById() {
    this.employeeService.getTaskById(this.taskId).subscribe((res) => {
      this.taskData = res;
    })
  }
  getCommentsByTaskId() {
    this.employeeService.getCommentsByTaskId(this.taskId).subscribe((res) => {
      this.comments = res;
    })
  }
  publishComment() {
    this.employeeService.createComment(this.taskId, this.commentForm.get("content")?.value).subscribe((res) => {
      if (res.id != null) {
        this.snackbar.open("Comment posted successfully", "Close", {duration: 5000});
        this.getCommentsByTaskId();
      } else {
        this.snackbar.open("Something went wrong", "Close", {duration: 5000});
      }
    })
  }
}
