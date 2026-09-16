'use client';

import { useEffect } from 'react';
import ReactMarkdown from 'react-markdown';
import remarkGfm from 'remark-gfm';
import rehypeHighlight from 'rehype-highlight';
import { useTheme } from '@/contexts/ThemeContext';

interface MarkdownContentProps {
  content: string;
}

export default function MarkdownContent({ content }: MarkdownContentProps) {
  const { colorMode } = useTheme();

  useEffect(() => {
    // Update highlight.js theme based on color mode
    let styleElement = document.querySelector('link[title="hljs-style"]') as HTMLLinkElement | null;
    if (!styleElement) {
      styleElement = document.createElement('link');
      styleElement.type = 'text/css';
      styleElement.rel = 'stylesheet';
      styleElement.title = 'hljs-style';
      document.head.appendChild(styleElement);
    }
    styleElement.href = `/highlight.js/styles/a11y-${colorMode}.min.css`;
  }, [colorMode]);

  return (
    <div className="markdown-content w-full">
      <ReactMarkdown remarkPlugins={[remarkGfm]} rehypePlugins={[rehypeHighlight]}>
        {content}
      </ReactMarkdown>
    </div>
  );
}
