import type { Metadata } from 'next';
import { ThemeProvider } from '@/contexts/ThemeContext';
import NavHeader from '@/components/NavHeader';
import './globals.css';

export const metadata: Metadata = {
  title: 'Jin Blog',
  description: "Jin's personal blog",
  icons: {
    icon: '/favicon.ico',
  },
};

export default function RootLayout({
  children,
}: {
  children: React.ReactNode;
}) {
  return (
    <html lang="ko" suppressHydrationWarning>
      <body className="bg-background-light dark:bg-background-dark text-content-light dark:text-content-dark">
        <ThemeProvider>
          <NavHeader />
          <main className="flex flex-col items-center w-full max-w-content mx-auto">
            {children}
          </main>
        </ThemeProvider>
      </body>
    </html>
  );
}
