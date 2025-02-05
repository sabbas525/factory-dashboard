import React from 'react';
import { useWebSocket } from '../hooks/useWebSocket';
import { LineCard } from './LineCard';
import { AlertFeed } from './AlertFeed';

export const Dashboard: React.FC = () => {
  const { lines, alerts } = useWebSocket('http://localhost:8080/ws');

  return (
    <div style={{ padding: 24, maxWidth: 1200, margin: '0 auto' }}>
      <h1 style={{ marginBottom: 24 }}>🏭 Factory Dashboard</h1>
      <div style={{ display: 'grid', gridTemplateColumns: 'repeat(auto-fit, minmax(280px, 1fr))', gap: 16, marginBottom: 24 }}>
        {lines.map(line => (
          <LineCard key={line.lineId} line={line} />
        ))}
      </div>
      <AlertFeed alerts={alerts} />
    </div>
  );
};
