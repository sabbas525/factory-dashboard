import React from 'react';
import { Alert } from '../types';

interface Props {
  alerts: Alert[];
}

export const AlertFeed: React.FC<Props> = ({ alerts }) => {
  return (
    <div style={{ border: '1px solid #e5e7eb', borderRadius: 8, padding: 16 }}>
      <h3 style={{ margin: '0 0 12px', color: '#ef4444' }}>⚠ Alerts</h3>
      {alerts.length === 0 && <p style={{ color: '#6b7280' }}>No active alerts</p>}
      <ul style={{ listStyle: 'none', padding: 0, margin: 0, maxHeight: 300, overflow: 'auto' }}>
        {alerts.map((alert, i) => (
          <li key={i} style={{ padding: '8px 0', borderBottom: '1px solid #f3f4f6', fontSize: 14 }}>
            <strong>{alert.lineId}</strong>: {alert.message}
            <br />
            <span style={{ fontSize: 12, color: '#6b7280' }}>
              {new Date(alert.timestamp).toLocaleTimeString()}
            </span>
          </li>
        ))}
      </ul>
    </div>
  );
};
