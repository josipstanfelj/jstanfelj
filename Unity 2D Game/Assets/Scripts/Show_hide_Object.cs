using UnityEngine;
using UnityEngine.Rendering.Universal;

public class showOrHideObject : MonoBehaviour
{
    public Light2D pointLight; // Referenca na Point Light 2D
    private SpriteRenderer spriteRenderer; // Renderer objekta
    private Vector2 targetPoint; // Ta�ka osvetljenja na objektu

    void Start()
    {
        spriteRenderer = GetComponent<SpriteRenderer>();

        if (spriteRenderer == null)
        {
            Debug.LogError("SpriteRenderer nije prona�en na objektu " + gameObject.name);
        }

        if (pointLight == null)
        {
            Debug.LogError("Point Light 2D nije povezano!");
        }

        // Odredi ciljnu ta�ku osvetljenja: Sredina X, dno Y
        Bounds bounds = spriteRenderer.bounds;
        targetPoint = new Vector2(bounds.center.x, bounds.min.y);
    }

    void Update()
    {
        if (spriteRenderer != null && pointLight != null)
        {
            // Proveri udaljenost izme�u svetla i ciljne ta�ke osvetljenja
            float distance = Vector2.Distance(targetPoint, pointLight.transform.position);

            if (distance <= pointLight.pointLightOuterRadius)
            {
                // Ako je unutar radijusa svetla, prika�i objekat
                spriteRenderer.enabled = true;

                // Opcionalno: Izmeni alfa vrednost u zavisnosti od osvetljenosti
                float intensity = Mathf.InverseLerp(pointLight.pointLightOuterRadius, pointLight.pointLightInnerRadius, distance);
                Color color = spriteRenderer.color;
                color.a = intensity; // Podesi prozirnost prema intenzitetu svetla
                spriteRenderer.color = color;
            }
            else
            {
                // Sakrij objekat ako nije osvetljen
                spriteRenderer.enabled = false;
            }
        }
    }
}
