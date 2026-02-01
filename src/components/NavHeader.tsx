'use client';

import Link from 'next/link';
import Image from 'next/image';
import { Moon, Sun } from 'lucide-react';
import { useTheme } from '@/contexts/ThemeContext';

export default function NavHeader() {
  const { colorMode, toggleColorMode } = useTheme();

  return (
    <header className="sticky top-0 z-[9998] w-full min-h-[3.75rem] bg-background-light dark:bg-background-dark">
      <div className="flex items-center justify-between w-full h-full px-8">
        <Link href="/" className="flex items-center cursor-pointer">
          <span className="text-[1.25rem] font-bold text-content-light dark:text-content-dark">
            Jin{' '}
          </span>
          <span className="text-[1.25rem] font-normal text-content-secondary-light dark:text-content-secondary-dark">
            Blog
          </span>
        </Link>

        <div className="flex items-center gap-4 text-[1.5rem]">
          <a
            href="https://github.com/parkjin0318"
            target="_blank"
            rel="noopener noreferrer"
            className="w-6 h-6 cursor-pointer"
          >
            <Image
              src={colorMode === 'dark' ? '/github-mark-white.svg' : '/github-mark.svg'}
              alt="github-mark"
              width={24}
              height={24}
              className="w-full h-full"
            />
          </a>

          <button
            onClick={toggleColorMode}
            className="w-6 h-6 cursor-pointer text-content-light dark:text-content-dark"
            aria-label="Toggle color mode"
          >
            {colorMode === 'light' ? <Moon size={24} /> : <Sun size={24} />}
          </button>
        </div>
      </div>

      <div className="w-full border-t border-divider-light dark:border-divider-dark" />
    </header>
  );
}
