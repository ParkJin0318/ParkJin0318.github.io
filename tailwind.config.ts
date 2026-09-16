import type { Config } from 'tailwindcss';

const config: Config = {
  content: [
    './src/pages/**/*.{js,ts,jsx,tsx,mdx}',
    './src/components/**/*.{js,ts,jsx,tsx,mdx}',
    './src/app/**/*.{js,ts,jsx,tsx,mdx}',
  ],
  darkMode: 'class',
  theme: {
    extend: {
      colors: {
        primary: '#576283',
        'primary-hover': '#49536f',
        content: {
          DEFAULT: '#212529',
          light: '#212529',
          dark: '#E5E5E5',
        },
        'content-secondary': {
          DEFAULT: '#6B7684',
          light: '#6B7684',
          dark: '#9CA3AF',
        },
        background: {
          light: '#FFFFFF',
          dark: '#1a1a1a',
        },
        divider: {
          light: '#E5E8EB',
          dark: '#333333',
        },
      },
      fontFamily: {
        pretendard: ['Pretendard', 'sans-serif'],
      },
      maxWidth: {
        content: '42.5rem',
      },
      fontSize: {
        'title-lg': ['2.5rem', { lineHeight: '1.3', fontWeight: '700' }],
        'title-md': ['1.5rem', { lineHeight: '1.4', fontWeight: '700' }],
        'body': ['1rem', { lineHeight: '1.7' }],
        'small': ['0.875rem', { lineHeight: '1.5' }],
      },
    },
  },
  plugins: [],
};

export default config;
