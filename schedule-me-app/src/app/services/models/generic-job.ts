import { Time } from "@angular/common";

export interface GenericJob {
    jobId : string
    name : string
    urgent : boolean
    startTime : Time
    endTime : Time
    status : string
    jobParam : any
    jobKind : string
}