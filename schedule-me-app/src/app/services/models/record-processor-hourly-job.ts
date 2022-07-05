import { Time } from "@angular/common";

export interface RecordProcessorHourlyJob {
    jobId : string
    name : string
    urgent : boolean
    startTime : Time
    status : string
    jobParam : any
    jobKind : string
    repeatFrequencyInMinutes : number
    lastCompleteRunStartedAt : Date
    lastCompletedRunEndedAt : Date
    recordCount : number
    recordProcessed : number
}