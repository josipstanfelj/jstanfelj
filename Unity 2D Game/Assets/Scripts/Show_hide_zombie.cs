using UnityEngine;

public class ZombieVisibility : MonoBehaviour
{
    public Transform player;  // Referenca na igraèa
    public float detectionRadius = 5f; // Radijus u kojem zombi postaje vidljiv
    public float fullVisibilityRadius = 1f; // Radijus unutar kojeg je zombi potpuno vidljiv
    private SpriteRenderer spriteRenderer;

    void Start()
    {
        spriteRenderer = GetComponent<SpriteRenderer>();

        if (spriteRenderer == null)
        {
            Debug.LogError("SpriteRenderer nije pronaðen na objektu " + gameObject.name);
            enabled = false;
            return;
        }

        if (player == null)
        {
            Debug.LogError("Player nije povezan!");
            enabled = false;
            return;
        }

        // Inicijalno sakrij zombija
        spriteRenderer.enabled = false;
    }

    void Update()
    {
        float distance = Vector2.Distance(transform.position, player.position);

        if (distance <= detectionRadius)
        {
            spriteRenderer.enabled = true;

            // Proraèunaj alfa vrednost zavisno od udaljenosti
            float intensity = Mathf.InverseLerp(detectionRadius, fullVisibilityRadius, distance);
            Color color = spriteRenderer.color;
            color.a = Mathf.Clamp01(intensity);
            spriteRenderer.color = color;
        }
        else
        {
            spriteRenderer.enabled = false;
        }
    }
}
