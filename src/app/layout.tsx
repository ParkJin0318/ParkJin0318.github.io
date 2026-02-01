import type { Metadata } from 'next';
import { ThemeProvider } from '@/contexts/ThemeContext';
import NavHeader from '@/components/NavHeader';
import './globals.css';

const SITE_URL = 'https://parkjin0318.github.io';
const SITE_NAME = 'Jin Blog';
const SITE_DESCRIPTION = '개발과 기술에 대한 이야기를 나누는 블로그입니다.';

export const metadata: Metadata = {
  metadataBase: new URL(SITE_URL),
  title: {
    default: SITE_NAME,
    template: `%s - ${SITE_NAME}`,
  },
  description: SITE_DESCRIPTION,
  keywords: ['개발', '프로그래밍', 'Android', 'Kotlin', '기술 블로그'],
  authors: [{ name: 'Jin Park' }],
  creator: 'Jin Park',
  openGraph: {
    type: 'website',
    locale: 'ko_KR',
    url: SITE_URL,
    siteName: SITE_NAME,
    title: SITE_NAME,
    description: SITE_DESCRIPTION,
  },
  twitter: {
    card: 'summary_large_image',
    title: SITE_NAME,
    description: SITE_DESCRIPTION,
  },
  robots: {
    index: true,
    follow: true,
    googleBot: {
      index: true,
      follow: true,
      'max-video-preview': -1,
      'max-image-preview': 'large',
      'max-snippet': -1,
    },
  },
  icons: {
    icon: '/favicon.svg',
  },
};

export default function RootLayout({
  children,
}: {
  children: React.ReactNode;
}) {
  return (
    <html lang="ko" suppressHydrationWarning>
      <body className="min-h-screen bg-background-light dark:bg-background-dark text-content-light dark:text-content-dark">
        <ThemeProvider>
          <NavHeader />
          <main>{children}</main>
        </ThemeProvider>
      </body>
    </html>
  );
}
