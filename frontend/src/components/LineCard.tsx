import React from 'react';
import { ProductionLine } from '../types';

interface Props {
  line: ProductionLine;
}

const statusColors: Record<string, string> = {
  RUNNING: '#22c55e',
  IDLE: '#eab308',
  MAINTENANCE: '#3b82f6',
  ERROR: '#ef4444',
};

export const LineCard: React.FC<Props> = ({ line }) => {
  return (
    <div style={{
      border: '1px solid #e5e7eb',
      borderRadius: 8,
      padding: 16,
      borderLeft: `4px solid ${statusColors[line.status] || '#6b7280'}`,
    }}>
      <h3 style={{ margin: '0 0 8px' }}>{line.name}</h3>
      <p style={{ margin: '4px 0', fontSize: 14 }}>
        Status: <strong>{line.status}</strong>
      </p>
      <p style={{ margin: '4px 0', fontSize: 14 }}>
        Throughput: <strong>{line.throughputPerHour} units/hr</strong>
      </p>
      <p style={{ margin: '4px 0', fontSize: 12, color: '#6b7280' }}>
        Updated: {new Date(line.lastUpdated).toLocaleTimeString()}
      </p>
    </div>
  );
};
