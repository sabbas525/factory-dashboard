export interface ProductionLine {
  lineId: string;
  name: string;
  status: 'RUNNING' | 'IDLE' | 'MAINTENANCE' | 'ERROR';
  throughputPerHour: number;
  lastUpdated: string;
}

export interface Alert {
  lineId: string;
  message: string;
  timestamp: string;
}

export interface ProductionEvent {
  type: 'STATUS_CHANGE' | 'THROUGHPUT_UPDATE' | 'ALERT';
  lineId: string;
  status?: string;
  throughput?: number;
  message?: string;
}
