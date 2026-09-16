interface PostHeaderProps {
  title: string;
  createdAt: string;
}

export default function PostHeader({ title, createdAt }: PostHeaderProps) {
  return (
    <header className="mb-12 pb-8 border-b border-divider-light dark:border-divider-dark">
      <h1 className="text-3xl md:text-4xl font-bold leading-tight text-content-light dark:text-content-dark">
        {title}
      </h1>
      <time className="mt-6 block text-sm text-content-secondary-light dark:text-content-secondary-dark">
        {createdAt}
      </time>
    </header>
  );
}
