// Convert kebab-case to snake_case
// Example: coroutine-introduction -> coroutine_introduction
export function toSnakeCase(slug: string): string {
  return slug.replace(/-/g, '_');
}

// Get thumbnail path from slug
export function getThumbnailPath(slug: string): string {
  return `/images/${toSnakeCase(slug)}/thumbnail.jpg`;
}
