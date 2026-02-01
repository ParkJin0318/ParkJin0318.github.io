'use client';

import { useEffect, useRef } from 'react';
import { useTheme } from '@/contexts/ThemeContext';

export default function PostComments() {
  const { colorMode } = useTheme();
  const containerRef = useRef<HTMLDivElement>(null);
  const utterancesLoaded = useRef(false);

  // Load utterances script once on mount
  useEffect(() => {
    if (!containerRef.current || utterancesLoaded.current) return;

    const container = containerRef.current;
    utterancesLoaded.current = true;

    const script = document.createElement('script');
    script.src = 'https://utteranc.es/client.js';
    script.setAttribute('repo', 'ParkJin0318/ParkJin0318.github.io');
    script.setAttribute('issue-term', 'pathname');
    script.setAttribute('label', 'comments');
    script.setAttribute('theme', colorMode === 'dark' ? 'github-dark' : 'github-light');
    script.setAttribute('crossorigin', 'anonymous');
    script.async = true;

    container.appendChild(script);
  }, []);

  // Update theme when colorMode changes
  useEffect(() => {
    const iframe = document.querySelector<HTMLIFrameElement>('.utterances-frame');
    if (iframe?.contentWindow) {
      const theme = colorMode === 'dark' ? 'github-dark' : 'github-light';
      iframe.contentWindow.postMessage(
        { type: 'set-theme', theme },
        'https://utteranc.es'
      );
    }
  }, [colorMode]);

  return <div ref={containerRef} className="w-full py-16" />;
}
