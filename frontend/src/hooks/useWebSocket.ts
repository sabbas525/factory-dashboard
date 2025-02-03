import { useEffect, useState, useCallback } from 'react';
import { Client } from '@stomp/stompjs';
import SockJS from 'sockjs-client';
import { ProductionLine, Alert, ProductionEvent } from '../types';

export function useWebSocket(url: string) {
  const [lines, setLines] = useState<ProductionLine[]>([]);
  const [alerts, setAlerts] = useState<Alert[]>([]);

  const fetchInitialData = useCallback(async () => {
    const res = await fetch('http://localhost:8080/api/lines');
    const data = await res.json();
    setLines(data);
  }, []);

  useEffect(() => {
    fetchInitialData();

    const client = new Client({
      webSocketFactory: () => new SockJS(url),
      onConnect: () => {
        client.subscribe('/topic/production-events', (msg) => {
          const event: ProductionEvent = JSON.parse(msg.body);

          if (event.type === 'STATUS_CHANGE') {
            setLines(prev => prev.map(l =>
              l.lineId === event.lineId ? { ...l, status: event.status as any, lastUpdated: new Date().toISOString() } : l
            ));
          } else if (event.type === 'THROUGHPUT_UPDATE') {
            setLines(prev => prev.map(l =>
              l.lineId === event.lineId ? { ...l, throughputPerHour: event.throughput!, lastUpdated: new Date().toISOString() } : l
            ));
          } else if (event.type === 'ALERT') {
            setAlerts(prev => [{ lineId: event.lineId, message: event.message!, timestamp: new Date().toISOString() }, ...prev].slice(0, 20));
          }
        });
      },
    });

    client.activate();
    return () => { client.deactivate(); };
  }, [url, fetchInitialData]);

  return { lines, alerts };
}
