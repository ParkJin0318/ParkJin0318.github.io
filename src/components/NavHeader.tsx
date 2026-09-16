'use client';

import Link from 'next/link';
import Image from 'next/image';
import { Moon, Sun } from 'lucide-react';
import { useTheme } from '@/contexts/ThemeContext';

export default function NavHeader() {
  const { colorMode, toggleColorMode } = useTheme();

  return (
    <header className="sticky top-0 z-[9998] w-full bg-background-light/80 dark:bg-background-dark/80 backdrop-blur-md">
      <div className="max-w-[50rem] mx-auto flex items-center justify-between px-6 py-5">
        <Link href="/" className="flex items-center gap-1 group">
          <span className="text-xl font-bold text-content-light dark:text-content-dark">
            Jin
          </span>
          <span className="text-xl font-medium text-primary">
            Blog
          </span>
        </Link>

        <div className="flex items-center gap-5">
          <a
            href="https://github.com/parkjin0318"
            target="_blank"
            rel="noopener noreferrer"
            className="opacity-70 hover:opacity-100 transition-opacity"
          >
            <Image
              src={colorMode === 'dark' ? '/github-mark-white.svg' : '/github-mark.svg'}
              alt="GitHub"
              width={22}
              height={22}
            />
          </a>

          <button
            onClick={toggleColorMode}
            className="opacity-70 hover:opacity-100 transition-opacity text-content-light dark:text-content-dark"
            aria-label="Toggle color mode"
          >
            {colorMode === 'light' ? <Moon size={22} /> : <Sun size={22} />}
          </button>
        </div>
      </div>
    </header>
  );
}
